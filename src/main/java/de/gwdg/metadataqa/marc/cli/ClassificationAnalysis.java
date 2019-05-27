package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.ValidatorParameters;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
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
  private Map<String, Map<String, Integer>> classifications = new TreeMap<>();
  private Map<Boolean, Integer> hasClassifications = new HashMap<>();
  private boolean readyToProcess;

  private static final List<String> fieldsWithIndicator2AndSubfield1 = Arrays.asList(
    "852"
  );

  private static final List<String> fieldsWithIndicator2AndSubfield2 = Arrays.asList(
    "600", "610", "611", "630", "647", "648", "650", "651", "655", "852"
  );

  private static final List<String> fieldsWithSubfield2 = Arrays.asList(
    "052", "055", "072", "084", "086"
  );

  private static final Map<String, String> fieldsWithScheme = new HashMap<>();
  static {
    fieldsWithScheme.put("080", "Universal Decimal Classification");
    fieldsWithScheme.put("082", "Dewey Decimal Classification");
    fieldsWithScheme.put("083", "Dewey Decimal Classification");
    fieldsWithScheme.put("085", "Dewey Decimal Classification");
    fieldsWithScheme.put("086", "Government Document Classification");
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
      System.err.println("ERROR. " + e.getLocalizedMessage());
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
    for (String field : fieldsWithIndicator2AndSubfield1) {
      if (!marcRecord.hasDatafield(field))
        continue;

      hasSchema = true;
      Map<String, Integer> fieldStatistics = getFieldStatistics(field);
      List<String> schemes = new ArrayList<>();
      for (String scheme : marcRecord.extract(field, "ind1")) {
        if (scheme.equals("Source specified in subfield $2")) {
          List<String> altSchemes = marcRecord.extract(field, "2", true);
          if (altSchemes.isEmpty()) {
            schemes.add("undetectable");
          } else {
            for (String altScheme : altSchemes) {
              schemes.add(altScheme);
            }
          }
        } else {
          schemes.add(scheme);
        }
      }
      addSchemesToStatistics(fieldStatistics, schemes);
    }

    for (String field : fieldsWithIndicator2AndSubfield2) {
      if (!marcRecord.hasDatafield(field))
        continue;

      hasSchema = true;
      Map<String, Integer> fieldStatistics = getFieldStatistics(field);
      List<String> schemes = new ArrayList<>();
      for (String scheme : marcRecord.extract(field, "ind2")) {
        if (scheme.equals("Source specified in subfield $2")) {
          List<String> altSchemes = marcRecord.extract(field, "2", true);
          if (altSchemes.isEmpty()) {
            schemes.add("undetectable");
          } else {
            for (String altScheme : altSchemes) {
              schemes.add(altScheme);
            }
          }
        } else {
          schemes.add(scheme);
        }
      }
      addSchemesToStatistics(fieldStatistics, schemes);
    }
    for (String field : fieldsWithSubfield2) {
      if (!marcRecord.hasDatafield(field))
        continue;

      hasSchema = true;
      Map<String, Integer> fieldStatistics = getFieldStatistics(field);
      List<String> schemes = marcRecord.extract(field, "2", true);
      if (schemes.isEmpty())
        schemes.add("undetectable");
      addSchemesToStatistics(fieldStatistics, schemes);
    }

    for (Map.Entry<String, String> entry : fieldsWithScheme.entrySet()) {
      final String field = entry.getKey();
      if (!marcRecord.hasDatafield(field))
        continue;

      hasSchema = true;
      Map<String, Integer> fieldStatistics = getFieldStatistics(field);
      List<DataField> fields = marcRecord.getDatafield(field);
      for (DataField dataField : fields){
        addSchemesToStatistics(fieldStatistics, Arrays.asList(entry.getValue()));
      }
    }

    if (!hasClassifications.containsKey(hasSchema)) {
      hasClassifications.put(hasSchema, 0);
    }
    hasClassifications.put(hasSchema, hasClassifications.get(hasSchema) + 1);
  }

  private void addSchemesToStatistics(Map<String, Integer> fieldStatistics, List<String> schemes) {
    for (String scheme : schemes) {
      if (!fieldStatistics.containsKey(scheme)) {
        fieldStatistics.put(scheme, 0);
      }
      fieldStatistics.put(scheme, fieldStatistics.get(scheme) + 1);
    }
  }

  private Map<String, Integer> getFieldStatistics(String field) {
    if (!classifications.containsKey(field)) {
      classifications.put(field, new HashMap<String, Integer>());
    }
    return classifications.get(field);
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
    char separator = ',';
    Path path = Paths.get(parameters.getOutputDir(), "classifications-by-field.csv");
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      classifications
        .entrySet()
        .stream()
        .forEach(entry -> {
            // System.err.println(entry.getKey());
            entry.getValue()
              .entrySet()
              .stream()
              .sorted((e1, e2) ->
                e2.getValue().compareTo(e1.getValue()))
              .forEach(
                e -> {
                  try {
                    writer.write(entry.getKey() + separator + "'" + e.getKey() + "'" + separator + e.getValue());
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

    path = Paths.get(parameters.getOutputDir(), "classifications-by-records.csv");
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      hasClassifications
        .entrySet()
        .stream()
        .sorted((e1, e2) ->
          e2.getValue().compareTo(e1.getValue()))
        .forEach(
          e -> {
            try {
              writer.write(e.getKey().toString() + separator + e.getValue());
            } catch (IOException ex) {
              ex.printStackTrace();
            }
          }
        );
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void printHelp(Options options) {

  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }
}
