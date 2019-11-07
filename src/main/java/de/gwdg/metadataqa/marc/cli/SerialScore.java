package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.Leader;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.ThompsonTraillAnalysis;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.SerialScoreParameters;
import de.gwdg.metadataqa.marc.cli.parameters.ThompsonTraillCompletenessParameters;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
import de.gwdg.metadataqa.marc.utils.Serial;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.marc4j.marc.Record;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

/**
 * usage:
 * java -cp target/metadata-qa-marc-0.1-SNAPSHOT-jar-with-dependencies.jar \
 * de.gwdg.metadataqa.marc.cli.ThompsonTraillCompleteness [MARC21 file]
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class SerialScore implements MarcFileProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(
    SerialScore.class.getCanonicalName()
  );
  private final Options options;
  private final boolean readyToProcess;
  private SerialScoreParameters parameters;
  private File output = null;

  public SerialScore(String[] args) throws ParseException {
    parameters = new SerialScoreParameters(args);
    System.err.println("marcxml: " + parameters.isMarcxml());
    options = parameters.getOptions();
    readyToProcess = true;
  }

  public static void main(String[] args) throws ParseException {
    MarcFileProcessor processor = null;
    try {
      processor = new SerialScore(args);
    } catch (ParseException e) {
      System.err.println("ERROR. " + e.getLocalizedMessage());
      processor.printHelp(processor.getParameters().getOptions());
      System.exit(0);
    }

    if (processor.getParameters().getArgs().length < 1) {
      System.err.println("Please provide a MARC file name!");
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
  public void beforeIteration() {
    logger.info(parameters.formatParameters());
    output = new File(parameters.getOutputDir(), parameters.getFileName());
    if (output.exists())
      output.delete();

    String header = StringUtils.join(ThompsonTraillAnalysis.getHeader(), ",") + "\n";
    print(header);
  }

  @Override
  public void fileOpened(Path path) {
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) {

  }

  @Override
  public void processRecord(MarcRecord marcRecord, int recordNumber) {
    if (marcRecord.getType().equals(Leader.Type.CONTINUING_RESOURCES)) {
      Serial serial = new Serial(marcRecord);
      int score = serial.determineRecordQualityScore();
      String message = String.format(
        "\"%s\",%d%n",
        marcRecord.getId(), score
      );
      print(message);
    }
  }

  @Override
  public void fileProcessed() {

  }

  @Override
  public void afterIteration() {

  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }

  public void printHelp(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    String message = String.format(
      "java -cp metadata-qa-marc.jar %s [options] [file]",
      this.getClass().getCanonicalName()
    );
    formatter.printHelp(message, options);
  }

  private void print(String message) {
    try {
      FileUtils.writeStringToFile(output, message, true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}