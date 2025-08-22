package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.analysis.contextual.authority.AuthorityAnalyzer;
import de.gwdg.metadataqa.marc.analysis.contextual.authority.AuthorityCategory;
import de.gwdg.metadataqa.marc.analysis.contextual.authority.AuthorityStatistics;
import de.gwdg.metadataqa.marc.analysis.contextual.authority.Marc21AuthorityAnalyzer;
import de.gwdg.metadataqa.marc.analysis.contextual.authority.PicaAuthorityAnalyzer;
import de.gwdg.metadataqa.marc.analysis.contextual.authority.UnimarcAuthorityAnalyzer;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.ValidatorParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.marc4j.marc.Record;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.gwdg.metadataqa.marc.Utils.count;
import static de.gwdg.metadataqa.marc.Utils.quote;

public class AuthorityAnalysis extends QACli<ValidatorParameters> implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(AuthorityAnalysis.class.getCanonicalName());

  private Map<Integer, Integer> histogram = new HashMap<>();
  private Map<Integer, String> frequencyExamples = new HashMap<>();
  private Map<Boolean, Integer> hasClassifications = new HashMap<>();
  private boolean readyToProcess;
  private static char separator = ',';
  AuthorityStatistics statistics = new AuthorityStatistics();

  public AuthorityAnalysis(String[] args) throws ParseException {
    parameters = new ValidatorParameters(args);
    readyToProcess = true;
    Schema.resetIdCounter();
  }

  public static void main(String[] args) {
    BibliographicInputProcessor processor = null;
    try {
      processor = new AuthorityAnalysis(args);
    } catch (ParseException e) {
      System.err.println(createRow("ERROR. ", e.getLocalizedMessage()));
      System.exit(1);
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
    var iterator = new RecordIterator(processor);
    iterator.setProcessWithErrors(processor.getParameters().getProcessRecordsWithoutId());
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
  public void processRecord(BibliographicRecord bibliographicRecord, int recordNumber, List<ValidationError> errors) throws IOException {
    processRecord(bibliographicRecord, recordNumber);
  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber) throws IOException {
    if (parameters.getRecordIgnorator().isIgnorable(marcRecord))
      return;

    // Depending on the type of record, create the appropriate analyzer
    AuthorityAnalyzer analyzer;
    if (parameters.isMarc21()) {
      analyzer = new Marc21AuthorityAnalyzer(marcRecord, statistics);
    } else if (parameters.isPica()) {
      analyzer = new PicaAuthorityAnalyzer(marcRecord, statistics);
    } else if (parameters.isUnimarc()) {
      analyzer = new UnimarcAuthorityAnalyzer(marcRecord, statistics);
    } else {
      logger.log(Level.SEVERE, "Unhandled schema type: {0}", new Object[]{parameters.getSchemaType()});
      return;
    }
    int count = analyzer.process();
    count((count > 0), hasClassifications);
    count(count, histogram);

    frequencyExamples.computeIfAbsent(count, s -> marcRecord.getId(true));
  }

  @Override
  public void beforeIteration() {
    // do nothing
  }

  @Override
  public void fileOpened(Path path) {
    // do nothing
  }

  @Override
  public void fileProcessed() {
    // do nothing
  }

  @Override
  public void afterIteration(int numberOfprocessedRecords, long duration) {
    printAuthoritiesByCategories();
    printAuthoritiesBySchema();
    printAuthoritiesByRecords();
    printAuthoritiesHistogram();
    printFrequencyExamples();
    printAuthoritiesSubfieldsStatistics();
    saveParameters("authorities.params.json", parameters, Map.of("numberOfprocessedRecords", numberOfprocessedRecords, "duration", duration));
  }

  private void printAuthoritiesByCategories() {
    var path = Paths.get(parameters.getOutputDir(), "authorities-by-categories.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("category", "recordcount", "instancecount"));
      statistics.getRecordsPerCategories()
        .entrySet()
        .stream()
        .sorted((e1, e2) -> Integer.compare(e1.getKey().getId(), e2.getKey().getId()))
        .forEach(
          entry -> {
            AuthorityCategory category = entry.getKey();
            int recordCount = entry.getValue();
            int instanceCount = statistics.getInstancesPerCategories().get(category);
            try {
              writer.write(createRow(
                quote(category.getLabel()),
                recordCount,
                instanceCount
              ));
            } catch (IOException | NullPointerException ex) {
              logger.log(Level.SEVERE, "build", ex);
              logger.severe(category.toString());
            }
          }
        );
    } catch (IOException e) {
      logger.log(Level.SEVERE, "printAuthoritiesByCategories", e);
    }
  }

  private void printAuthoritiesBySchema() {
    var path = Paths.get(parameters.getOutputDir(), "authorities-by-schema.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("id", "field", "location", "scheme", "abbreviation", "abbreviation4solr", "recordcount", "instancecount"));
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
      logger.log(Level.SEVERE, "printAuthoritiesBySchema", e);
    }
  }

  private void printSingleClassificationBySchema(BufferedWriter writer, Map.Entry<Schema, Integer> entry) {
    Schema schema = entry.getKey();
    int instanceCount = entry.getValue();
    int recordCount = statistics.getRecords().get(schema);
    try {
      writer.write(createRow(
        schema.getId(),
        schema.getField(),
        schema.getLocation(),
        '"' + schema.getSchema().replace("\"", "\\\"") + '"',
        schema.getAbbreviation(),
        Utils.solarize(schema.getAbbreviation()),
        recordCount,
        instanceCount
      ));
    } catch (IOException | NullPointerException e) {
      logger.log(Level.SEVERE, "printSingleClassificationBySchema", e);
      System.err.println(schema);
    }
  }

  private void printAuthoritiesByRecords() {
    Path path;
    path = Paths.get(parameters.getOutputDir(), "authorities-by-records.csv");
    try (var writer = Files.newBufferedWriter(path)) {
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
              logger.log(Level.SEVERE, "printAuthoritiesByRecords", ex);
            }
          }
        );
    } catch (IOException e) {
      logger.log(Level.SEVERE, "printAuthoritiesByRecords", e);
    }
  }

  private void printAuthoritiesHistogram() {
    var path = Paths.get(parameters.getOutputDir(), "authorities-histogram.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("count", "frequency"));
      histogram
        .entrySet()
        .stream()
        .sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
        .forEach(
          entry -> {
            try {
              writer.write(createRow(entry.getKey(), entry.getValue()));
            } catch (IOException e) {
              logger.log(Level.SEVERE, "printAuthoritiesHistogram", e);
            }
          }
        );
    } catch (IOException e) {
      logger.log(Level.SEVERE, "printAuthoritiesHistogram", e);
    }
  }

  private void printFrequencyExamples() {
    var path = Paths.get(parameters.getOutputDir(), "authorities-frequency-examples.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("count", "id"));
      frequencyExamples
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

  private void printAuthoritiesSubfieldsStatistics() {
    var path = Paths.get(parameters.getOutputDir(), "authorities-by-schema-subfields.csv");
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
      logger.log(Level.SEVERE, "printAuthoritiesSubfieldsStatistics", e);
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

  private static String createRow(List<String> fields) {
    return StringUtils.join(fields, separator) + "\n";
  }

  private static String createRow(Object... fields) {
    return StringUtils.join(fields, separator) + "\n";
  }

  @Override
  public void printHelp(Options options) {
    // do nothing
  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }
}
