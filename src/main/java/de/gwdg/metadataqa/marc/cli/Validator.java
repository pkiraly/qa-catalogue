package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.cli.parameters.ValidatorParameters;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormatter;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.marc4j.marc.Record;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat.TAB_SEPARATED;

/**
 * usage:
 * java -cp target/metadata-qa-marc-0.1-SNAPSHOT-jar-with-dependencies.jar de.gwdg.metadataqa.marc.cli.Validator [MARC21 file]
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Validator implements MarcFileProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(Validator.class.getCanonicalName());
  private Options options;

  private ValidatorParameters parameters;
  private Map<String, Integer> errorCounter = new TreeMap<>();
  private File detailsFile = null;
  private File summaryFile = null;
  private boolean doPrintInProcessRecord = true;
  private Path currentFile;
  private boolean readyToProcess;

  public Validator(String[] args) throws ParseException {
    parameters = new ValidatorParameters(args);
    options = parameters.getOptions();
    errorCounter = new TreeMap<>();
    readyToProcess = true;
  }

  public static void main(String[] args) {
    MarcFileProcessor processor = null;
    try {
      processor = new Validator(args);
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

  public void printHelp(Options opions) {
    HelpFormatter formatter = new HelpFormatter();
    String message = String.format("java -cp metadata-qa-marc.jar %s [options] [file]", this.getClass().getCanonicalName());
    formatter.printHelp(message, options);
  }

  @Override
  public ValidatorParameters getParameters() {
    return parameters;
  }

  @Override
  public void beforeIteration() {
    logger.info(parameters.formatParameters());
    if (!parameters.useStandardOutput()) {
      detailsFile = prepareReportFile(parameters.getFileName());
      logger.info("details output: " + detailsFile.getPath());
      if (parameters.getSummaryFileName() != null) {
        summaryFile = prepareReportFile(parameters.getSummaryFileName());
        logger.info("summary output: " + summaryFile.getPath());
      } else {
        if (parameters.doSummary())
          summaryFile = detailsFile;
      }
    }
    if (parameters.doDetails()) {
      String header = ValidationErrorFormatter.formatHeader(parameters.getFormat());
      print(detailsFile, header + "\n");
    }
  }

  private File prepareReportFile(String fileName) {
    File reportFile = new File(fileName);
    if (reportFile.exists())
      reportFile.delete();
    return reportFile;
  }

  @Override
  public void fileOpened(Path currentFile) {
    this.currentFile = currentFile;
  }

  @Override
  public void fileProcessed() {

  }

  @Override
  public void afterIteration() {
    char separator = parameters.getFormat().equals(TAB_SEPARATED) ? '\t' : ',';
    if (parameters.doSummary()) {
      String header = ValidationErrorFormatter.formatHeaderForSummary(
        parameters.getFormat()
      );
      print(summaryFile, header + "\n");
      for (Map.Entry<String, Integer> entry : errorCounter.entrySet()) {
        print(summaryFile, String.format("%s%s%d%n", entry.getKey(), separator, entry.getValue()));
      }
    }
  }

  private void print(File file, String message) {
    if (parameters.useStandardOutput())
      System.out.print(message);
    else {
      try {
        FileUtils.writeStringToFile(file, message, true);
      } catch (IOException e) {
        if (parameters.doLog())
          logger.severe(e.toString());
        e.printStackTrace();
      }
    }
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {

  }

  @Override
  public void processRecord(MarcRecord marcRecord, int i) {
    if (marcRecord.getId() == null)
      logger.severe("No record number at " + i);

    boolean isValid = marcRecord.validate(parameters.getMarcVersion(), parameters.doSummary());
    if (!isValid && doPrintInProcessRecord) {
      if (parameters.doSummary()) {
        List<String> errors = ValidationErrorFormatter.formatForSummary(
          marcRecord.getValidationErrors(), parameters.getFormat()
        );
        for (String error : errors) {
          if (!errorCounter.containsKey(error)) {
            errorCounter.put(error, 0);
          }
          errorCounter.put(error, errorCounter.get(error) + 1);
        }
      }
      if (parameters.doDetails()) {
        String message = ValidationErrorFormatter.format(
          marcRecord.getValidationErrors(), parameters.getFormat(), parameters.getTrimId()
        );
        print(detailsFile, message);
      }
    }
  }

  public boolean doPrintInProcessRecord() {
    return doPrintInProcessRecord;
  }

  public void setDoPrintInProcessRecord(boolean doPrintInProcessRecord) {
    this.doPrintInProcessRecord = doPrintInProcessRecord;
  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }
}