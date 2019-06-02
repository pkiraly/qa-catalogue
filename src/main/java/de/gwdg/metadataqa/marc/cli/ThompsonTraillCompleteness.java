package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.ThompsonTraillAnalysis;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.ThompsonTraillCompletenessParameters;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
import org.apache.commons.cli.*;
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
public class ThompsonTraillCompleteness implements MarcFileProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(
    ThompsonTraillCompleteness.class.getCanonicalName()
  );
  private final Options options;
  private final boolean readyToProcess;
  private ThompsonTraillCompletenessParameters parameters;
  private File output = null;

  public ThompsonTraillCompleteness(String[] args) throws ParseException {
    parameters = new ThompsonTraillCompletenessParameters(args);
    System.err.println("tt().marcxml: " + parameters.isMarcxml());
    options = parameters.getOptions();
    readyToProcess = true;
  }

  public static void main(String[] args) throws ParseException {
    System.err.println("main(): " + StringUtils.join(args, " "));
    MarcFileProcessor processor = null;
    try {
      processor = new ThompsonTraillCompleteness(args);
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
    List<Integer> scores = ThompsonTraillAnalysis.getScores(marcRecord);
    String id = parameters.getTrimId()
              ? marcRecord.getId().trim()
              : marcRecord.getId();
    String message = String.format(
      "\"%s\",%s%n",
      id, StringUtils.join(scores, ",")
    );
    print(message);
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