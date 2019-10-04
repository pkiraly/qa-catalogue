package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.ValidatorParameters;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.ClassificationSchemes;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jetbrains.annotations.NotNull;
import org.marc4j.marc.Record;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

public class ClassificationAnalysis implements MarcFileProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(ClassificationAnalysis.class.getCanonicalName());

  private final Options options;
  private CommonParameters parameters;
  private Map<Schema, Integer> schemaInstanceStatistics = new HashMap<>();
  private Map<Schema, Integer> schemaRecordStatistics = new HashMap<>();
  private Map<Schema, Map<List<String>, Integer>> schemaSubfieldsStatistics = new HashMap<>();
  private Map<String, Map<String[], Integer>> fieldInstanceStatistics = new TreeMap<>();
  private Map<Boolean, Integer> hasClassifications = new HashMap<>();
  private Map<String[], Integer> fieldInRecordsStatistics = new HashMap<>();
  private boolean readyToProcess;
  private ClassificationSchemes classificationSchemes = ClassificationSchemes.getInstance();
  private static char separator = ',';


  private static final List<String> fieldsWithIndicator1AndSubfield2 = Arrays.asList(
    "052", // Geographic Classification
    "086", // Government Document Classification Number
    "852"  // Location
  );

  private static final List<String> fieldsWithIndicator2AndSubfield2 = Arrays.asList(
    // "055", // Classification Numbers Assigned in Canada
    "072", // Subject Category Code
    "600", // Subject Added Entry - Personal Name
    "610", // Subject Added Entry - Corporate Name
    "611", // Subject Added Entry - Meeting Name
    "630", // Subject Added Entry - Uniform Title
    "647", // Subject Added Entry - Named Event
    "648", // Subject Added Entry - Chronological Term
    "650", // Subject Added Entry - Topical Term
    "651", // Subject Added Entry - Geographic Name
    "655"  // Index Term - Genre/Form
  );

  // 052 is bad here
  // 055 $2 -- Used only when the second indicator contains value 6 (Other call number assigned by LAC), 7 (Other class number assigned by LAC), 8 (Other call number assigned by the contributing library), or 9 (Other class number assigned by the contributing library).
  private static final List<String> fieldsWithSubfield2 = Arrays.asList(
    "084"  // Other Classificaton Number
  );

  private static final Map<String, String> fieldsWithScheme = new HashMap<>();
  static {
    fieldsWithScheme.put("080", "Universal Decimal Classification");
    fieldsWithScheme.put("082", "Dewey Decimal Classification");
    fieldsWithScheme.put("083", "Dewey Decimal Classification");
    fieldsWithScheme.put("085", "Dewey Decimal Classification");
    // fieldsWithScheme.put("086", "Government Document Classification");
  }

  public ClassificationAnalysis(String[] args) throws ParseException {
    parameters = new ValidatorParameters(args);
    options = parameters.getOptions();
    readyToProcess = true;
  }

  public static void main(String[] args) {
    MarcFileProcessor processor = null;
    try {
      processor = new ClassificationAnalysis(args);
    } catch (ParseException e) {
      System.err.println(createRow("ERROR. ", e.getLocalizedMessage()));
      // processor.printHelp(processor.getParameters().getOptions());
      System.exit(0);
    }
    if (processor.getParameters().getArgs().length < 1) {
      System.err.println("Please provide a MARC file name!");
      processor.printHelp(processor.getParameters().getOptions());
      System.exit(0);
    }
    if (processor.getParameters().doHelp()) {
      processor.printHelp(processor.getParameters().getOptions());
      System.exit(0);
    }
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();
  }

  @Override
  public CommonParameters getParameters() {
    return parameters;
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {

  }

  @Override
  public void processRecord(MarcRecord marcRecord, int recordNumber) throws IOException {
    boolean hasSchema = false;
    for (String tag : fieldsWithIndicator1AndSubfield2) {
      hasSchema = processFieldWithIndicator1AndSubfield2(marcRecord, hasSchema, tag);
    }

    for (String tag : fieldsWithIndicator2AndSubfield2) {
      hasSchema = processFieldWithIndicator2AndSubfield2(marcRecord, hasSchema, tag);
    }

    for (String tag : fieldsWithSubfield2) {
      hasSchema = processFieldWithSubfield2(marcRecord, hasSchema, tag);
    }

    for (Map.Entry<String, String> fieldEntry : fieldsWithScheme.entrySet()) {
      hasSchema = processFieldWithScheme(marcRecord, hasSchema, fieldEntry);
    }

    if (!hasClassifications.containsKey(hasSchema)) {
      hasClassifications.put(hasSchema, 0);
    }
    hasClassifications.put(hasSchema, hasClassifications.get(hasSchema) + 1);
  }

  private boolean processFieldWithScheme(MarcRecord marcRecord, boolean hasSchema, Map.Entry<String, String> fieldEntry) {
    final String tag = fieldEntry.getKey();
    if (!marcRecord.hasDatafield(tag))
      return hasSchema;

    hasSchema = true;
    Map<String[], Integer> fieldStatistics = getFieldInstanceStatistics(tag);
    List<DataField> fields = marcRecord.getDatafield(tag);
    List<Schema> schemas = new ArrayList<>();
    for (DataField field : fields) {
      // System.err.println(dataField.getInd1());
      String first = null;
      String alt = null;
      for (MarcSubfield subfield : field.getSubfields()) {
        String code = subfield.getCode();
        if (!code.equals("1") && !code.equals("2") && !code.equals("6") && !code.equals("8")) {
          first = "$" + code;
          break;
        } else {
          if (alt == null)
            alt = "$" + code;
        }
      }
      if (first != null) {
        String scheme = fieldEntry.getValue();
        Schema currentSchema = new Schema(tag, first, scheme, classificationSchemes.resolve(scheme));
        schemas.add(currentSchema);
        updateSchemaSubfieldStatistics(field, currentSchema);
      } else {
        logger.severe("undetected subfield: " + field.toString());
      }
    }
    addSchemasToStatistics(schemaInstanceStatistics, schemas);
    addSchemasToStatistics(schemaRecordStatistics, deduplicateSchema(schemas));
    return hasSchema;
  }

  private boolean processFieldWithIndicator1AndSubfield2(MarcRecord marcRecord, boolean hasSchema, String tag) {
    if (!marcRecord.hasDatafield(tag))
      return hasSchema;

    Map<String[], Integer> fieldStatistics = getFieldInstanceStatistics(tag);
    List<Schema> schemas = new ArrayList<>();
    List<DataField> fields = marcRecord.getDatafield(tag);
    for (DataField field : fields) {
      String scheme = field.resolveInd1();
      if (scheme.equals("No information provided"))
        continue;

      if (!tag.equals("852"))
        hasSchema = true;

      Schema currentSchema = null;
      if (isaReferenceToSubfield2(tag, scheme)) {
        currentSchema = extractSchemaFromSubfield2(tag, schemas, field);
      } else {
        currentSchema = new Schema(tag, "ind1", scheme, classificationSchemes.resolve(scheme));
        schemas.add(currentSchema);
      }
      updateSchemaSubfieldStatistics(field, currentSchema);
    }

    addSchemasToStatistics(schemaInstanceStatistics, schemas);
    addSchemasToStatistics(schemaRecordStatistics, deduplicateSchema(schemas));
    return hasSchema;
  }

  private boolean processFieldWithIndicator2AndSubfield2(MarcRecord marcRecord, boolean hasSchema, String tag) {
    if (!marcRecord.hasDatafield(tag))
      return false;

    hasSchema = true;
    Map<String[], Integer> fieldStatistics = getFieldInstanceStatistics(tag);
    List<Schema> schemas = new ArrayList<>();
    List<DataField> fields = marcRecord.getDatafield(tag);
    for (DataField field : fields) {
      String scheme = field.resolveInd2();
      Schema currentSchema = null;
      if (isaReferenceToSubfield2(tag, scheme)) {
        currentSchema = extractSchemaFromSubfield2(tag, schemas, field);
      } else {
        if (scheme.equals(" ")) {
          logger.severe(marcRecord.getId() + " " + field.toString());
        } else {
          currentSchema = new Schema(tag, "ind2", scheme, classificationSchemes.resolve(scheme));
          schemas.add(currentSchema);
        }
      }
      updateSchemaSubfieldStatistics(field, currentSchema);
    }
    addSchemasToStatistics(schemaInstanceStatistics, schemas);
    addSchemasToStatistics(schemaRecordStatistics, deduplicateSchema(schemas));
    return hasSchema;
  }

  private boolean processFieldWithSubfield2(MarcRecord marcRecord, boolean hasSchema, String tag) {
    if (!marcRecord.hasDatafield(tag))
      return false;

    hasSchema = true;
    List<DataField> fields = marcRecord.getDatafield(tag);
    List<Schema> schemas = new ArrayList<>();
    for (DataField field : fields) {
      Schema currentSchema = extractSchemaFromSubfield2(tag, schemas, field);
      updateSchemaSubfieldStatistics(field, currentSchema);
    }
    addSchemasToStatistics(schemaInstanceStatistics, schemas);
    addSchemasToStatistics(schemaRecordStatistics, deduplicateSchema(schemas));
    return hasSchema;
  }

  @NotNull
  private Schema extractSchemaFromSubfield2(String tag, List<Schema> schemas, DataField field) {
    Schema currentSchema = null;
    List<MarcSubfield> altSchemes = field.getSubfield("2");
    if (altSchemes == null || altSchemes.isEmpty()) {
      currentSchema = new Schema(tag, "$2", "undetectable");
      schemas.add(currentSchema);
    } else {
      for (MarcSubfield altScheme : altSchemes) {
        currentSchema = new Schema(tag, "$2", altScheme.getValue(), altScheme.resolve());
        schemas.add(currentSchema);
      }
    }
    return currentSchema;
  }

  private void updateSchemaSubfieldStatistics(DataField field, Schema currentSchema) {
    if (currentSchema == null)
      return;
    List<String> subfields = new ArrayList<>();
    Set<String> multiFields = new HashSet<>();
    for (MarcSubfield subfield : field.getSubfields()) {
      String code = subfield.getCode();
      if (!subfields.contains(code))
        subfields.add(code);
      else
        multiFields.add(code);
    }
    if (!multiFields.isEmpty()) {
      for (String code : multiFields)
        subfields.remove(code);
      for (String code : multiFields)
        subfields.add(code + "+");
    }
    Collections.sort(subfields);

    if (!schemaSubfieldsStatistics.containsKey(currentSchema)) {
      schemaSubfieldsStatistics.put(currentSchema, new HashMap<List<String>, Integer>());
    }
    Map<List<String>, Integer> subfieldsStatistics = schemaSubfieldsStatistics.get(currentSchema);
    if (!subfieldsStatistics.containsKey(subfields)) {
      subfieldsStatistics.put(subfields, 1);
    } else {
      subfieldsStatistics.put(subfields, subfieldsStatistics.get(subfields) + 1);
    }
  }

  private List<Schema> deduplicateSchema(List<Schema> schemas) {
    Set<Schema> set = new HashSet<Schema>(schemas);
    List<Schema> deduplicated = new ArrayList<Schema>();
    deduplicated.addAll(new HashSet<Schema>(schemas));
    return deduplicated;
  }

  private boolean isaReferenceToSubfield2(String field, String scheme) {
    return ((field.equals("055") && isAReferenceFrom055(scheme)) || scheme.equals("Source specified in subfield $2"));
  }

  private boolean isAReferenceFrom055(String scheme) {
    return (scheme.equals("Other call number assigned by LAC")
            || scheme.equals("Other class number assigned by LAC")
            || scheme.equals("Other call number assigned by the contributing library")
            || scheme.equals("Other class number assigned by the contributing library"));
  }

  private void addSchemasToStatistics(Map<Schema, Integer> fieldStatistics, List<Schema> schemes) {
    if (!schemes.isEmpty()) {
      for (Schema scheme : schemes) {
        if (!fieldStatistics.containsKey(scheme)) {
          fieldStatistics.put(scheme, 0);
        }
        fieldStatistics.put(scheme, fieldStatistics.get(scheme) + 1);
      }
    }
  }

  private void addSchemesToStatistics(Map<String[], Integer> fieldStatistics, List<String[]> schemes) {
    if (!schemes.isEmpty()) {
      for (String[] scheme : schemes) {
        if (!fieldStatistics.containsKey(scheme)) {
          fieldStatistics.put(scheme, 0);
          if (!fieldInRecordsStatistics.containsKey(scheme)) {
            fieldInRecordsStatistics.put(scheme, 0);
          }
          fieldInRecordsStatistics.put(scheme, fieldInRecordsStatistics.get(scheme) + 1);
        }
        fieldStatistics.put(scheme, fieldStatistics.get(scheme) + 1);
      }
    }
  }

  private Map<String[], Integer> getFieldInstanceStatistics(String field) {
    if (!fieldInstanceStatistics.containsKey(field)) {
      fieldInstanceStatistics.put(field, new HashMap<String[], Integer>());
    }
    return fieldInstanceStatistics.get(field);
  }

  @Override
  public void beforeIteration() {

  }

  @Override
  public void fileOpened(Path path) {

  }

  @Override
  public void fileProcessed() {

  }

  @Override
  public void afterIteration() {
    Path path = Paths.get(parameters.getOutputDir(), "classifications-by-field.csv");
    /*
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      writer.write(String.format("field%sscheme%scount\n", separator, separator));
      fieldInstanceStatistics
        .entrySet()
        .stream()
        .forEach(fieldEntry -> {
            // System.err.println(entry.getKey());
            fieldEntry.getValue()
              .entrySet()
              .stream()
              .sorted((e1, e2) ->
                e2.getValue().compareTo(e1.getValue()))
              .forEach(
                classificationStatistics -> {
                  try {
                    // int perRecord = fieldInRecordsStatistics.get(entry.getKey());
                    String schema = classificationStatistics.getKey()[0];
                    String location = classificationStatistics.getKey()[1];
                    int count = classificationStatistics.getValue();
                    writer.write(String.format("%s%s'%s'%s%d\n",
                      fieldEntry.getKey(), separator, location, separator, schema, separator, count
                    ));
                  } catch (IOException ex) {
                    ex.printStackTrace();
                  }
                }
              );
          }
        );
    } catch (IOException e) {
      e.printStackTrace();
    }
    */

    path = Paths.get(parameters.getOutputDir(), "classifications-by-schema.csv");
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("field", "location", "scheme", "abbreviation", "recordcount", "instancecount"));
      schemaInstanceStatistics
        .entrySet()
        .stream()
        .sorted((e1, e2) -> {
            int i = e1.getKey().field.compareTo(e2.getKey().field);
            if (i != 0)
              return i;
            else {
              i = e1.getKey().location.compareTo(e2.getKey().location);
              if (i != i)
                return i;
              else
                return e2.getValue().compareTo(e1.getValue());
            }
          }
        )
        .forEach(
          entry -> {
            Schema schema = entry.getKey();
            int instanceCount = entry.getValue();
            int recordCount = schemaRecordStatistics.get(schema);
            try {
              writer.write(createRow(
                schema.field,
                schema.location,
                '"' + schema.schema.replace("\"", "\\\"") + '"',
                schema.abbreviation,
                recordCount,
                instanceCount
              ));
            } catch (IOException ex) {
              ex.printStackTrace();
            }
          }

          /*
            schemaEntry. -> {
            // System.err.println(entry.getKey());
              schemaEntry.entrySet()
              .stream()
              .sorted((e1, e2) ->
                e2.getValue().compareTo(e1.getValue()))
              .forEach(
                classificationStatistics -> {
                  try {
                    // int perRecord = fieldInRecordsStatistics.get(entry.getKey());
                    String schema = classificationStatistics.getKey()[0];
                    String location = classificationStatistics.getKey()[1];
                    int count = classificationStatistics.getValue();
                    writer.write(String.format("%s%s'%s'%s%d\n",
                      fieldEntry.getKey(), separator, location, separator, schema, separator, count
                    ));
                  } catch (IOException ex) {
                    ex.printStackTrace();
                  }
                }
              );
          }
           */
        );
    } catch (IOException e) {
      e.printStackTrace();
    }

    path = Paths.get(parameters.getOutputDir(), "classifications-by-records.csv");
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("records-with-classification", "count"));
      hasClassifications
        .entrySet()
        .stream()
        .sorted((e1, e2) ->
          e2.getValue().compareTo(e1.getValue()))
        .forEach(
          e -> {
            try {
              writer.write(createRow(e.getKey().toString(), e.getValue()));
            } catch (IOException ex) {
              ex.printStackTrace();
            }
          }
        );
    } catch (IOException e) {
      e.printStackTrace();
    }

    path = Paths.get(parameters.getOutputDir(), "classifications-by-schema-subfields.csv");
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      final List<String> header = Arrays.asList("field", "location", "label", "abbreviation", "subfields", "scount");
      writer.write(createRow(header));
      schemaSubfieldsStatistics
        .entrySet()
        .stream()
        .sorted((e1, e2) ->
          e1.getKey().field.compareTo(e2.getKey().field))
        .forEach(
          schemaEntry -> {
              Schema schema = schemaEntry.getKey();
              Map<List<String>, Integer> val = schemaEntry.getValue();
              val
                .entrySet()
                .stream()
                .sorted((count1, count2) -> count2.getValue().compareTo(count1.getValue()))
                .forEach(
                  countEntry -> {
                    List<String> subfields = countEntry.getKey();
                    int count = countEntry.getValue();
                    try {
                      writer.write(createRow(
                        schema.field,
                        schema.location,
                        '"' + schema.schema.replace("\"", "\\\"") + '"',
                        schema.abbreviation,
                        StringUtils.join(subfields, ';'),
                        count
                      ));
                    } catch (IOException ex) {
                      ex.printStackTrace();
                    }
                  }
                );
          }
        );
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @NotNull
  private static String createRow(List<String> fields) {
    return StringUtils.join(fields, separator) + "\n";
  }

  private static String createRow(Object... fields) {
    return StringUtils.join(fields, separator) + "\n";
  }

  @Override
  public void printHelp(Options options) {

  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }

  private class Schema {
    String field;
    String location;
    String schema;
    String abbreviation;

    public Schema(String field, String location, String schema) {
      this.field = field;
      this.location = location;
      this.schema = schema;
    }

    public Schema(String field, String location, String schema, String abbreviation) {
      this(field, location, schema);
      this.abbreviation = abbreviation;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;

      if (o == null || getClass() != o.getClass()) return false;

      Schema schema1 = (Schema) o;

      return new EqualsBuilder()
        .append(field, schema1.field)
        .append(location, schema1.location)
        .append(schema, schema1.schema)
        .isEquals();
    }

    @Override
    public int hashCode() {
      return new HashCodeBuilder(17, 37)
        .append(field)
        .append(location)
        .append(schema)
        .toHashCode();
    }
  }

  private class Counter {
    int recordCount;
    int instanceCount;
  }

  // private
}
