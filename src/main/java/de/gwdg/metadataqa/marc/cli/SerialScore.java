package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.analysis.serial.Marc21Serial;
import de.gwdg.metadataqa.marc.analysis.serial.MarcSerial;
import de.gwdg.metadataqa.marc.analysis.serial.SerialFields;
import de.gwdg.metadataqa.marc.analysis.serial.UnimarcSerial;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.SerialScoreParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.MarcLeader;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.dao.record.MarcRecord;
import de.gwdg.metadataqa.marc.dao.record.UnimarcRecord;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.gwdg.metadataqa.marc.Utils.createRow;
import static de.gwdg.metadataqa.marc.Utils.quote;

/**
 * usage:
 * java -cp target/qa-catalogue-0.1-SNAPSHOT-jar-with-dependencies.jar \
 * de.gwdg.metadataqa.marc.cli.SerialScore [MARC21 file]
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class SerialScore extends QACli<SerialScoreParameters> implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(
    SerialScore.class.getCanonicalName()
  );
  private final Options options;
  private final boolean readyToProcess;
  private File output = null;
  private Map<Integer, Integer> histogram = new HashMap<>();

  public SerialScore(String[] args) throws ParseException {
    parameters = new SerialScoreParameters(args);
    options = parameters.getOptions();
    readyToProcess = true;
  }

  public static void main(String[] args) {
    BibliographicInputProcessor processor = null;
    try {
      processor = new SerialScore(args);
    } catch (ParseException e) {
      logger.severe("ERROR. " + e.getLocalizedMessage());
      System.exit(1);
    }

    if (processor.getParameters().getArgs().length < 1) {
      logger.severe("Please provide a MARC file name!");
      System.exit(0);
    }
    if (processor.getParameters().doHelp()) {
      processor.printHelp(processor.getParameters().getOptions());
      System.exit(0);
    }

    RecordIterator iterator = new RecordIterator(processor);
    iterator.setProcessWithErrors(processor.getParameters().getProcessRecordsWithoutId());
    iterator.start();
  }

  @Override
  public CommonParameters getParameters() {
    return parameters;
  }

  @Override
  public void beforeIteration() {
    logger.info(() -> parameters.formatParameters());
    printFields();

    output = new File(parameters.getOutputDir(), parameters.getFileName());
    if (output.exists()) {
      try {
        Files.delete(output.toPath());
      } catch (IOException e) {
        logger.log(Level.SEVERE, "The output file ({}) has not been deleted", output.getAbsolutePath());
      }
    }

    print(createRow(MarcSerial.getHeaders()));
  }

  @Override
  public void fileOpened(Path path) {
    // do nothing
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) {
    // do nothing
  }

  @Override
  public void processRecord(BibliographicRecord bibliographicRecord, int recordNumber, List<ValidationError> errors) throws IOException {
    processRecord(bibliographicRecord, recordNumber);
  }

  @Override
  public void processRecord(BibliographicRecord bibliographicRecord, int recordNumber) {
    if (!(bibliographicRecord instanceof MarcRecord)) {
      return;
    }

    MarcRecord marcRecord = (MarcRecord) bibliographicRecord;
    if (!marcRecord.getType().equals(MarcLeader.Type.CONTINUING_RESOURCES)) {
      return;
    }

    if (parameters.getRecordIgnorator().isIgnorable(marcRecord)) {
      return;
    }

    MarcSerial serial;
    if (marcRecord instanceof Marc21Record) {
      serial = new Marc21Serial((Marc21Record) marcRecord);
    } else {
      serial = new UnimarcSerial((UnimarcRecord) marcRecord);
    }

    // Count the scores
    List<Integer> scores = serial.determineRecordQualityScore();

    // Create the histogram message
    String message = createRow(
      quote(marcRecord.getId().trim()), StringUtils.join(scores, ",")
    );
    print(message);
  }

  @Override
  public void fileProcessed() {
    // do nothing
  }

  @Override
  public void afterIteration(int numberOfprocessedRecords, long duration) {
    printHistogram();
    saveParameters("serials.params.json", parameters, Map.of("numberOfprocessedRecords", numberOfprocessedRecords, "duration", duration));
  }

  private void printHistogram() {
    // FIXME histogram is never updated
    Path path;
    path = Paths.get(parameters.getOutputDir(), "serial-histogram.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("score", "frequency"));
      histogram
        .entrySet()
        .stream()
        .sorted(Map.Entry.comparingByKey())
        .forEach(
          entry -> {
            try {
              writer.write(createRow(entry.getKey(), entry.getValue()));
            } catch (IOException e) {
              logger.log(Level.SEVERE, "printHistogram", e);
            }
          }
        );
    } catch (IOException e) {
      logger.log(Level.SEVERE, "printHistogram", e);
    }
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
      logger.log(Level.SEVERE, "print", e);
    }
  }

  private void printFields() {
    var path = Paths.get(parameters.getOutputDir(), "serial-score-fields.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("name", "transformed"));
      for (SerialFields field : SerialFields.values()) {
        try {
          writer.write(createRow(quote(field.getLabel()), field.getMachine()));
        } catch (IOException e) {
          logger.log(Level.SEVERE, "printFields", e);
        }
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, "printFields", e);
    }
  }
}
