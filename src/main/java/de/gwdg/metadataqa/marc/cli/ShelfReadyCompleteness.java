package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.analysis.shelfready.ShelfReadyAnalysis;
import de.gwdg.metadataqa.marc.analysis.shelfready.ShelfReadyFieldsBooks;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.ShelfReadyCompletenessParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.PicaRecord;
import de.gwdg.metadataqa.marc.dao.record.UnimarcRecord;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.marc4j.marc.Record;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.gwdg.metadataqa.marc.Utils.createRow;
import static de.gwdg.metadataqa.marc.Utils.quote;

/**
 * usage:
 * java -cp target/qa-catalogue-0.1-SNAPSHOT-jar-with-dependencies.jar \
 * de.gwdg.metadataqa.marc.cli.ShelfReadyCompleteness [MARC21 file]
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class ShelfReadyCompleteness extends QACli<ShelfReadyCompletenessParameters> implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(
    ShelfReadyCompleteness.class.getCanonicalName()
  );
  private final Options options;
  private final boolean readyToProcess;
  private File output = null;

  public ShelfReadyCompleteness(String[] args) throws ParseException {
    parameters = new ShelfReadyCompletenessParameters(args);
    options = parameters.getOptions();
    readyToProcess = true;
  }

  public static void main(String[] args) {
    BibliographicInputProcessor processor = null;
    try {
      processor = new ShelfReadyCompleteness(args);
    } catch (ParseException e) {
      System.err.println("ERROR. " + e.getLocalizedMessage());
      System.exit(1);
    }

    if (processor.getParameters().getArgs().length < 1) {
      System.err.println("Please provide a MARC file name!");
      System.exit(1);
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
  public void beforeIteration() {
    logger.info(parameters.formatParameters());
    printFields();

    output = new File(parameters.getOutputDir(), parameters.getFileName());
    if (output.exists()) {
      try {
        Files.delete(output.toPath());
      } catch (IOException e) {
        logger.log(Level.SEVERE, "The output file ({}) has not been deleted", output.getAbsolutePath());
      }
    }
    print(createRow(createHeaders()));
  }

  private List<String> createHeaders() {
    List<String> headers = new ArrayList<>();
    headers.add("id");
    headers.addAll(ShelfReadyAnalysis.getHeaders());
    headers.add("total");
    return headers;
  }

  @Override
  public void fileOpened(Path path) {
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) {
    // do nothing
  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber, List<ValidationError> errors) throws IOException {
    // do nothing
  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber) {
    if (parameters.getRecordIgnorator().isIgnorable(marcRecord))
      return;

    List<Double> scores = ShelfReadyAnalysis.getScores(marcRecord);
    String id = parameters.getTrimId()
              ? marcRecord.getId().trim()
              : marcRecord.getId();

    List<String> scoresToString = new ArrayList<>();
    for (Double score : scores)
      scoresToString.add(String.format(Locale.ENGLISH, "%.2f", score));

    String message = String.format(
      "\"%s\",%s%n",
      id, StringUtils.join(scoresToString, ",")
    );
    print(message);
  }

  @Override
  public void fileProcessed() {

  }

  @Override
  public void afterIteration(int numberOfprocessedRecords, long duration) {
    saveParameters("shelf-ready-completeness.params.json", parameters, Map.of("numberOfprocessedRecords", numberOfprocessedRecords, "duration", duration));
  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }

  public void printHelp(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    String message = String.format(
      "java -cp qa-catalogue.jar %s [options] [file]",
      this.getClass().getCanonicalName()
    );
    formatter.printHelp(message, options);
  }

  private void print(String message) {
    try {
      FileUtils.writeStringToFile(output, message, StandardCharsets.UTF_8, true);
    } catch (IOException e) {
      logger.log(Level.WARNING, "print", e);
    }
  }

  private void printFields() {
    var path = Paths.get(parameters.getOutputDir(), "shelf-ready-completeness-fields.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("name", "label", "marcpath", "score"));
      Map<ShelfReadyFieldsBooks, Map<String, List<String>>> map;

      if (parameters.getSchemaType() == SchemaType.MARC21) {
        map = (new Marc21BibliographicRecord()).getShelfReadyMap();
      } else if (parameters.getSchemaType() == SchemaType.PICA) {
        map = (new PicaRecord()).getShelfReadyMap();
      } else if (parameters.getSchemaType() == SchemaType.UNIMARC) {
        map = (new UnimarcRecord()).getShelfReadyMap();
      } else {
        throw new IllegalArgumentException("Unknown schema type: " + parameters.getSchemaType());
      }
      for (Map.Entry<ShelfReadyFieldsBooks, Map<String, List<String>>> field : map.entrySet()) {
        ShelfReadyFieldsBooks category = field.getKey();
        String paths = transformPaths(field.getValue());
        try {
          writer.write(createRow(category.name(), quote(category.getLabel()), quote(paths), category.getScore()));
        } catch (IOException e) {
          logger.log(Level.WARNING, "printFields", e);
        }
      }
    } catch (IOException e) {
      logger.log(Level.WARNING, "printFields", e);
    }
  }

  private String transformPaths(Map<String, List<String>> value) {
    List<String> paths = new ArrayList<>();
    for (Map.Entry<String, List<String>> field : value.entrySet()) {
      if (field.getValue() == null || field.getValue().isEmpty()) {
        paths.add(field.getKey());
        continue;
      }

      for (String code : field.getValue()) {
        paths.add(field.getKey() + "$" + code);
      }
    }
    return StringUtils.join(paths, ",");
  }
}
