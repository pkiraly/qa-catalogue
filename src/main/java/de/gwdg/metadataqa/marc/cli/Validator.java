package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.cli.parameters.ValidatorParameters;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormatter;
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
import java.util.*;
import java.util.logging.Logger;

import static de.gwdg.metadataqa.marc.Utils.count;
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
  private Map<String, Counter> errorCounter = new TreeMap<>();
  private Map<ValidationError, Integer> vErrorCounter = new HashMap<>();
  private Map<Integer, Integer> hashedIndex = new HashMap<>();
  private Map<Integer, Set<String>> errorCollector = new TreeMap<>();
  private File detailsFile = null;
  private File summaryFile = null;
  private File collectorFile = null;
  private boolean doPrintInProcessRecord = true;
  private Path currentFile;
  private boolean readyToProcess;
  private int counter;
  private char separator;
  private boolean hasSeparator = false;
  private int vErrorId = 1;

  public Validator(String[] args) throws ParseException {
    parameters = new ValidatorParameters(args);
    options = parameters.getOptions();
    errorCounter = new TreeMap<>();
    readyToProcess = true;
    counter = 0;

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
    String message = String.format("java -cp metadata-qa-marc.jar %s [options] [file]",
      this.getClass().getCanonicalName());
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
      detailsFile = prepareReportFile(parameters.getOutputDir(), parameters.getDetailsFileName());
      logger.info("details output: " + detailsFile.getPath());
      if (parameters.getSummaryFileName() != null) {
        summaryFile = prepareReportFile(parameters.getOutputDir(), parameters.getSummaryFileName());
        logger.info("summary output: " + summaryFile.getPath());
        collectorFile = prepareReportFile(parameters.getOutputDir(), "issue-collector.csv");
        String header = ValidationErrorFormatter.formatHeaderForCollector(
          parameters.getFormat()
        );
        print(collectorFile, header + "\n");

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

  private File prepareReportFile(String outputDir, String fileName) {
    File reportFile = new File(outputDir, fileName);
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
  public void afterIteration(int numberOfprocessedRecords) {
    char separator = getSeparator();
    if (parameters.doSummary()) {
      String header = ValidationErrorFormatter.formatHeaderForSummary(
        parameters.getFormat()
      );
      print(summaryFile, header + "\n");
      for (Map.Entry<ValidationError, Integer> entry : vErrorCounter.entrySet()) {
        ValidationError error = entry.getKey();
        int count = entry.getValue();
        String formattedOutput = ValidationErrorFormatter.formatForSummary(
          error, parameters.getFormat()
        );
        print(summaryFile, Utils.createRow(separator, error.getId(), formattedOutput, count));
      }
      /*
      for (Map.Entry<String, Counter> entry : errorCounter.entrySet()) {
        Counter counter = entry.getValue();
        print(summaryFile, Utils.createRow(separator, counter.id, entry.getKey(), counter.count));
      }
      */

      /*
      header = ValidationErrorFormatter.formatHeaderForCollector(
        parameters.getFormat()
      );
      print(collectorFile, header + "\n");
      */
      for (Map.Entry<Integer, Set<String>> entry : errorCollector.entrySet()) {
        printCollectorEntry(entry.getKey(), entry.getValue());
      }
    }
  }

  private char getSeparator() {
    if (!hasSeparator) {
      separator = parameters.getFormat().equals(TAB_SEPARATED) ? '\t' : ',';
    }
    return separator;
  }

  private void printCollectorEntry(Integer errorId, Set<String> recordIds) {
    print(
      collectorFile,
      String.format(
        "%d%s%s%n",
        errorId, separator, StringUtils.join(recordIds, ";")
      )
    );
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
    if (i % 100000 == 0)
      logger.info("Number of error types so far: " + vErrorCounter.size());

    boolean isValid = marcRecord.validate(parameters.getMarcVersion(), parameters.doSummary());
    if (!isValid && doPrintInProcessRecord) {
      if (parameters.doSummary()) {
        List<ValidationError> vErrors = marcRecord.getValidationErrors();
        for (ValidationError error : vErrors) {
          if (!vErrorCounter.containsKey(error)) {
            error.setId(vErrorId++);
            hashedIndex.put(error.hashCode(), error.getId());
          } else {
            error.setId(hashedIndex.get(error.hashCode()));
          }
          count(error, vErrorCounter);
          updateErrorCollector(marcRecord.getId(true), error.getId());
        }

        /*
        List<String> errors = ValidationErrorFormatter.formatForSummary(
          marcRecord.getValidationErrors(), parameters.getFormat()
        );
        for (String error : errors) {
          if (!errorCounter.containsKey(error)) {
            errorCounter.put(error, new Counter(0, counter++));
          }
          errorCounter.get(error).count++;

          int current = errorCounter.get(error).id;
          updateErrorCollector(marcRecord, current);
        }
         */
      }
      if (parameters.doDetails()) {
        if (parameters.doSummary()) {
          Map<Integer, Integer> errorIds = new HashMap<>();
          // List<Integer> errorIds = new ArrayList<>(marcRecord.getValidationErrors().size());
          for (ValidationError error : marcRecord.getValidationErrors()) {
            if (error.getId() == null)
              error.setId(hashedIndex.get(error.hashCode()));
            count(error.getId(), errorIds);
            // errorIds.add(error.getId());
          }
          String message = ValidationErrorFormatter.formatSimple(
            marcRecord.getId(parameters.getTrimId()), parameters.getFormat(), errorIds
          );
          print(detailsFile, message);
        } else {
          String message = ValidationErrorFormatter.format(
            marcRecord.getValidationErrors(), parameters.getFormat(), parameters.getTrimId()
          );
          print(detailsFile, message);
        }
      }
    }
  }

  private void updateErrorCollector(String recordId, int errorId) {
    if (!errorCollector.containsKey(errorId)) {
      errorCollector.put(errorId, new HashSet<String>());
    } else if (parameters.doEmptyLargeCollectors()) {
      if (errorCollector.get(errorId).size() >= 1000) {
        printCollectorEntry(errorId, errorCollector.get(errorId));
        errorCollector.put(errorId, new HashSet<String>());
      }
    }
    errorCollector.get(errorId).add(recordId);
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

  private class Counter {
    int id;
    int count;

    public Counter(int count, int id) {
      this.count = count;
      this.id = id;
    }
  }
}