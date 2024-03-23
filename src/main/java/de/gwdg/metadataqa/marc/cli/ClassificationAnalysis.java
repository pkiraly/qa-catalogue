package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.analysis.contextual.classification.ClassificationAnalyzer;
import de.gwdg.metadataqa.marc.analysis.contextual.classification.ClassificationStatistics;
import de.gwdg.metadataqa.marc.analysis.contextual.classification.Marc21ClassificationAnalyzer;
import de.gwdg.metadataqa.marc.analysis.contextual.classification.PicaClassificationAnalyzer;
import de.gwdg.metadataqa.marc.analysis.contextual.classification.UnimarcClassificationAnalyzer;
import de.gwdg.metadataqa.marc.cli.parameters.ClassificationParameters;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.Collocation;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.utils.pica.PicaSubjectManager;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.marc4j.marc.Record;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.gwdg.metadataqa.marc.Utils.createRow;

public class ClassificationAnalysis extends QACli<ClassificationParameters> implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(ClassificationAnalysis.class.getCanonicalName());

  private final Options options;
  private boolean readyToProcess;
  private static char separator = ',';
  private File collectorFile;
  ClassificationStatistics statistics = new ClassificationStatistics();

  public ClassificationAnalysis(String[] args) throws ParseException {
    parameters = new ClassificationParameters(args);
    options = parameters.getOptions();
    readyToProcess = true;
    Schema.resetIdCounter();
  }

  public static void main(String[] args) {
    BibliographicInputProcessor processor = null;
    try {
      processor = new ClassificationAnalysis(args);
    } catch (ParseException e) {
      logger.severe(createRow("ERROR. ", e.getLocalizedMessage()));
      System.exit(1);
    }
    if (processor.getParameters().getArgs().length < 1) {
      logger.severe("Please provide a MARC file name!");
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
    // do nothing
  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber, List<ValidationError> errors) throws IOException {
    // do nothing
  }

  @Override
  public void processRecord(BibliographicRecord bibliographicRecord, int recordNumber) throws IOException {
    if (parameters.getRecordIgnorator().isIgnorable(bibliographicRecord))
      return;

    ClassificationAnalyzer analyzer;
    if (parameters.isPica()) {
      analyzer = new PicaClassificationAnalyzer(bibliographicRecord, statistics, parameters);
    } else if (parameters.isUnimarc()) {
      analyzer = new UnimarcClassificationAnalyzer(bibliographicRecord, statistics, parameters);
    } else {
      analyzer = new Marc21ClassificationAnalyzer(bibliographicRecord, statistics, parameters);
    }

    analyzer.process();

    /*
    List<Schema> schemas = analyzer.getSchemasInRecord();
    if (!schemas.isEmpty()) {
      List<String> abbreviations = schemas
        .stream()
        .map(Schema::getAbbreviation)
        .distinct()
        .collect(Collectors.toList());
      if (!abbreviations.isEmpty()) {
        String joined = StringUtils.join(abbreviations, ":");
        printToFile(collectorFile, Utils.createRow(marcRecord.getId(true), joined));
      }
    }
    */
  }

  @Override
  public void beforeIteration() {
    // Method not used
  }

  @Override
  public void fileOpened(Path path) {
    // Method not used
  }

  @Override
  public void fileProcessed() {
    // Method not used
  }

  @Override
  public void afterIteration(int numberOfprocessedRecords, long duration) {
    printClassificationsBySchema();
    printClassificationsByRecords();
    printClassificationsHistogram();
    printFrequencyExamples();
    printSchemaSubfieldsStatistics();
    if (parameters.doCollectCollocations())
      printClassificationsCollocation();
    copySchemaFileToOutputDir();
    saveParameters("classifications.params.json", parameters, Map.of("numberOfprocessedRecords", numberOfprocessedRecords, "duration", duration));
  }

  private void copySchemaFileToOutputDir() {
    if (parameters.isPica()) {
      File source = new File(PicaSubjectManager.getSchemaFile());
      try {
        FileUtils.copyFileToDirectory(source, new File(parameters.getOutputDir()));
      } catch (IOException e) {
        logger.warning(e.getLocalizedMessage());
      }
    }
  }

  private void printClassificationsCollocation() {
    Path path;
    path = Paths.get(parameters.getOutputDir(), "classifications-collocations.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(Collocation.header());
      Integer total1 = statistics.getHasClassifications().getOrDefault(true, Integer.valueOf(0));
      Integer total = statistics.recordCountWithClassification();
      logger.info(() -> "total: " + total);
      if (!total1.equals(total))
        logger.log(Level.SEVERE, "total from hasClassifications ({0}) != from collation ({1})", new Object[]{total1, total});

      statistics.getCollocationHistogram()
        .entrySet()
        .stream()
        .map(e -> new Collocation(e.getKey(), e.getValue(), total))
        .sorted((e1, e2) -> e1.compareTo(e2) * -1)
        .forEach(entry -> printCollocation(writer, entry));
    } catch (IOException e) {
      logger.log(Level.SEVERE, "printClassificationsCollocation", e);
    }
  }

  private void printCollocation(BufferedWriter writer, Collocation entry) {
    try {
      writer.write(entry.formatRow());
    } catch (IOException e) {
      logger.log(Level.SEVERE, "printCollocation", e);
    }
  }

  private void printClassificationsBySchema() {
    Path path;
    path = Paths.get(parameters.getOutputDir(), "classifications-by-schema.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("id", "field", "location", "scheme",
        "abbreviation", "abbreviation4solr", "recordcount", "instancecount",
        "type"
      ));
      statistics.getInstances()
        .entrySet()
        .stream()
        .sorted((e1, e2) -> {
            int i = e1.getKey().getField().compareTo(e2.getKey().getField());
            if (i != 0)
              return i;
            else {
              i = e1.getKey().getLocation().compareTo(e2.getKey().getLocation());
              if (i != 0)
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
      logger.log(Level.SEVERE, "printClassificationsBySchema", e);
    }
  }

  private void printSingleClassificationBySchema(BufferedWriter writer,
                                                 Map.Entry<Schema, Integer> entry) {
    Schema schema = entry.getKey();
    int instanceCount = entry.getValue();
    int recordCount = statistics.getRecords().get(schema);
    try {
      writer.write(createRow(
        schema.getId(),
        schema.getField(),
        schema.getLocation(),
        '"' + schema.getSchema().replace("\"", "\"\"") + '"',
        '"' + schema.getAbbreviation().replace("\"", "\"\"") + '"',
        Utils.solarize(schema.getAbbreviation()),
        recordCount,
        instanceCount,
        (schema.getType() == null ? "UNKNOWN" : schema.getType())
      ));
    } catch (IOException | NullPointerException ex) {
      logger.log(Level.SEVERE, "printClassificationsBySchema", ex);
      logger.severe(schema.toString());
    }
  }

  private void printClassificationsByRecords() {
    Path path;
    path = Paths.get(parameters.getOutputDir(), "classifications-by-records.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("records-with-classification", "count"));
      statistics.getHasClassifications()
        .entrySet()
        .stream()
        .sorted((e1, e2) ->
          e2.getValue().compareTo(e1.getValue()))
        .forEach(
          e -> {
            try {
              writer.write(createRow(e.getKey().toString(), e.getValue()));
            } catch (IOException ex) {
              logger.log(Level.SEVERE, "printClassificationsByRecords", ex);
            }
          }
        );
    } catch (IOException e) {
      logger.log(Level.SEVERE, "printClassificationsByRecords", e);
    }
  }

  private void printClassificationsHistogram() {
    var path = Paths.get(parameters.getOutputDir(), "classifications-histogram.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("count", "frequency"));
      statistics.getSchemaHistogram()
        .entrySet()
        .stream()
        .sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
        .forEach(
          entry -> {
            try {
              writer.write(createRow(entry.getKey(), entry.getValue()));
            } catch (IOException e) {
              logger.log(Level.SEVERE, "printClassificationsHistogram", e);
            }
          }
        );
    } catch (IOException e) {
      logger.log(Level.SEVERE, "printClassificationsHistogram", e);
    }
  }

  private void printFrequencyExamples() {
    var path = Paths.get(parameters.getOutputDir(), "classifications-frequency-examples.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("count", "id"));
      statistics.getFrequencyExamples()
        .entrySet()
        .stream()
        .sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
        .forEach(
          entry -> {
            try {
              writer.write(createRow(entry.getKey(), entry.getValue()));
            } catch (IOException e) {
              logger.log(Level.SEVERE, "printFrequencyExamples", e);
            }
          }
        );
    } catch (IOException e) {
      logger.log(Level.SEVERE, "printFrequencyExamples", e);
    }
  }

  private void printSchemaSubfieldsStatistics() {
    Path path;
    path = Paths.get(parameters.getOutputDir(), "classifications-by-schema-subfields.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      // final List<String> header = Arrays.asList("field", "location", "label", "abbreviation", "subfields", "scount");
      final List<String> header = Arrays.asList("id", "subfields", "count");
      writer.write(createRow(header));
      statistics.getSubfields()
        .entrySet()
        .stream()
        .sorted((e1, e2) ->
          e1.getKey().getField().compareTo(e2.getKey().getField()))
        .forEach(
          schemaEntry -> printSingleSchemaSubfieldsStatistics(writer, schemaEntry)
        );
    } catch (IOException e) {
      logger.log(Level.SEVERE, "printSchemaSubfieldsStatistics", e);
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
              schema.getId(),
              // schema.field,
              // schema.location,
              // '"' + schema.schema.replace("\"", "\\\"") + '"',
              // schema.abbreviation,
              StringUtils.join(subfields, ';'),
              count
            ));
          } catch (IOException ex) {
            logger.log(Level.SEVERE, "printSingleSchemaSubfieldsStatistics", ex);
          }
        }
      );
  }

  @Override
  public void printHelp(Options options) {
    // Method not used
  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }

  public ClassificationStatistics getStatistics() {
    return statistics;
  }
}
