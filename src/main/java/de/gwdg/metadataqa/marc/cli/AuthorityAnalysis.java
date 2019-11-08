package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.ValidatorParameters;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;
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
import java.util.regex.Pattern;

import static de.gwdg.metadataqa.marc.Utils.count;

public class AuthorityAnalysis implements MarcFileProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(AuthorityAnalysis.class.getCanonicalName());

  private static int SCHEMA_COUNTER = 0;
  private final Options options;
  private CommonParameters parameters;
  private Map<Schema, Integer> authoritiesInstanceStatistics = new HashMap<>();
  private Map<Schema, Integer> authoritiesCounter = new HashMap<>();
  private Map<Schema, Integer> authoritiesRecordStatistics = new HashMap<>();
  private Map<Integer, Integer> histogram = new HashMap<>();
  private Map<Schema, Map<List<String>, Integer>> authoritiesSubfieldsStatistics = new HashMap<>();
  private Map<Boolean, Integer> hasClassifications = new HashMap<>();
  private boolean readyToProcess;
  private static char separator = ',';
  private static Pattern NUMERIC = Pattern.compile("^\\d");

  public AuthorityAnalysis(String[] args) throws ParseException {
    parameters = new ValidatorParameters(args);
    options = parameters.getOptions();
    readyToProcess = true;
  }

  public static void main(String[] args) {
    MarcFileProcessor processor = null;
    try {
      processor = new AuthorityAnalysis(args);
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
    int count = 0;
    for (DataField field : marcRecord.getAuthorityFields()) {
      SourceSpecificationType type = field.getDefinition().getSourceSpecificationType();
      if (type == null) {

      } else if (type.equals(SourceSpecificationType.Subfield2)) {
        count += processFieldWithSubfield2(field);
      } else {
        logger.severe("Unhandled type: " + type);
      }
    }

    count((count > 0), hasClassifications);
    count(count, histogram);
  }

  private int processFieldWithSubfield2(DataField field) {
    int count = 0;

    List<Schema> schemas = new ArrayList<>();
    Schema currentSchema = extractSchemaFromSubfield2(field.getTag(), schemas, field);
    updateSchemaSubfieldStatistics(field, currentSchema);
    count++;

    addSchemasToStatistics(authoritiesInstanceStatistics, schemas);
    addSchemasToStatistics(authoritiesRecordStatistics, deduplicateSchema(schemas));
    return count;
  }

  @NotNull
  private Schema extractSchemaFromSubfield2(String tag, List<Schema> schemas, DataField field) {
    Schema currentSchema = null;
    List<MarcSubfield> altSchemes = field.getSubfield("2");
    if (altSchemes == null || altSchemes.isEmpty()) {
      currentSchema = new Schema(tag, "$2", "undetectable", "undetectable");
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
    List<String> subfields = orderSubfields(field.getSubfields());

    if (!authoritiesSubfieldsStatistics.containsKey(currentSchema)) {
      authoritiesSubfieldsStatistics.put(currentSchema, new HashMap<List<String>, Integer>());
    }
    Map<List<String>, Integer> subfieldsStatistics = authoritiesSubfieldsStatistics.get(currentSchema);
    if (!subfieldsStatistics.containsKey(subfields)) {
      subfieldsStatistics.put(subfields, 1);
    } else {
      subfieldsStatistics.put(subfields, subfieldsStatistics.get(subfields) + 1);
    }
  }

  @NotNull
  private List<String> orderSubfields(List<MarcSubfield> originalSubfields) {
    List<String> subfields = new ArrayList<>();
    Set<String> multiFields = new HashSet<>();
    for (MarcSubfield subfield : originalSubfields) {
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

    List<String> alphabetic = new ArrayList<>();
    List<String> numeric = new ArrayList<>();
    for (String subfield : subfields) {
      if (NUMERIC.matcher(subfield).matches()) {
        numeric.add(subfield);
      } else {
        alphabetic.add(subfield);
      }
    }
    if (!numeric.isEmpty()) {
      Collections.sort(alphabetic);
      Collections.sort(numeric);
      subfields = alphabetic;
      subfields.addAll(numeric);
    } else {
      Collections.sort(subfields);
    }
    return subfields;
  }

  private List<Schema> deduplicateSchema(List<Schema> schemas) {
    Set<Schema> set = new HashSet<Schema>(schemas);
    List<Schema> deduplicated = new ArrayList<Schema>();
    deduplicated.addAll(new HashSet<Schema>(schemas));
    return deduplicated;
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
    printAuthoritiesBySchema();
    printAuthoritiesByRecords();
    printAuthoritiesHistogram();
    printAuthoritiesSubfieldsStatistics();
  }

  private void printAuthoritiesBySchema() {
    Path path;
    path = Paths.get(parameters.getOutputDir(), "authorities-by-schema.csv");
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("id", "field", "location", "scheme", "abbreviation", "abbreviation4solr", "recordcount", "instancecount"));
      authoritiesInstanceStatistics
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
          entry -> printSingleClassificationBySchema(writer, entry)
        );
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void printSingleClassificationBySchema(BufferedWriter writer, Map.Entry<Schema, Integer> entry) {
    Schema schema = entry.getKey();
    int instanceCount = entry.getValue();
    int recordCount = authoritiesRecordStatistics.get(schema);
    try {
      writer.write(createRow(
        schema.id,
        schema.field,
        schema.location,
        '"' + schema.schema.replace("\"", "\\\"") + '"',
        schema.abbreviation,
        Utils.solarize(schema.abbreviation),
        recordCount,
        instanceCount
      ));
    } catch (IOException ex) {
      ex.printStackTrace();
      System.err.println(schema);
    } catch (NullPointerException ex) {
      ex.printStackTrace();
      System.err.println(schema);
    }
  }

  private void printAuthoritiesByRecords() {
    Path path;
    path = Paths.get(parameters.getOutputDir(), "authorities-by-records.csv");
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("records-with-authorities", "count"));
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
  }

  private void printAuthoritiesHistogram() {
    Path path = Paths.get(parameters.getOutputDir(), "authorities-histogram.csv");
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("count", "frequency"));
      histogram
        .entrySet()
        .stream()
        .sorted((e1, e2) -> {
          return e1.getKey().compareTo(e2.getKey());
        })
        .forEach(
          entry -> {
            try {
              writer.write(createRow(entry.getKey(), entry.getValue()));
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        );
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  private void printAuthoritiesSubfieldsStatistics() {
    Path path = Paths.get(parameters.getOutputDir(), "authorities-by-schema-subfields.csv");
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      // final List<String> header = Arrays.asList("field", "location", "label", "abbreviation", "subfields", "scount");
      final List<String> header = Arrays.asList("id", "subfields", "count");
      writer.write(createRow(header));
      authoritiesSubfieldsStatistics
        .entrySet()
        .stream()
        .sorted((e1, e2) ->
          e1.getKey().field.compareTo(e2.getKey().field))
        .forEach(
          schemaEntry -> printSingleSchemaSubfieldsStatistics(writer, schemaEntry)
        );
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void printSingleSchemaSubfieldsStatistics(BufferedWriter writer, Map.Entry<Schema, Map<List<String>, Integer>> schemaEntry) {
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
              schema.id,
              // schema.field,
              // schema.location,
              // '"' + schema.schema.replace("\"", "\\\"") + '"',
              // schema.abbreviation,
              StringUtils.join(subfields, ';'),
              count
            ));
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      );
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
    int id;
    String field;
    String location;
    String schema;
    String abbreviation;

    public Schema(String field, String location, String schema) {
      this.field = field;
      this.location = location;
      this.schema = schema;
      setId();
    }

    public Schema(String field, String location, String abbreviation, String schema) {
      this(field, location, schema);
      this.abbreviation = abbreviation;
    }

    private void setId() {
      if (!authoritiesCounter.containsKey(this)) {
        authoritiesCounter.put(this, ++SCHEMA_COUNTER);
      }
      this.id = authoritiesCounter.get(this);
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

    @Override
    public String toString() {
      return "Schema{" +
              "id=" + id +
              ", field='" + field + '\'' +
              ", location='" + location + '\'' +
              ", schema='" + schema + '\'' +
              ", abbreviation='" + abbreviation + '\'' +
              '}';
    }
  }

  private class Counter {
    int recordCount;
    int instanceCount;
  }

  // private
}
