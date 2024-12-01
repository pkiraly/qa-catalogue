package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.analysis.DataElementCounter;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class DataElements implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(DataElements.class.getCanonicalName());
  private static final Pattern dataFieldPattern = Pattern.compile("^(\\d\\d\\d)\\$(.*)$");

  private final Options options;
  private CompletenessParameters parameters;
  private Map<String, Integer> library003Counter = new TreeMap<>();
  private Map<String, Integer> libraryCounter = new TreeMap<>();
  private Map<String, Map<String, Integer>> packageCounter = new TreeMap<>();
  private Map<String, Map<String, Integer>> elementCardinality = new TreeMap<>();
  private Map<String, Map<String, Integer>> elementFrequency = new TreeMap<>();
  private Map<String, Map<Integer, Integer>> fieldHistogram = new HashMap<>();
  private boolean readyToProcess;
  private DataElementCounter dataElementCounter;
  private File outputFile;

  public DataElements(String[] args) throws ParseException {
    parameters = new CompletenessParameters(args);
    options = parameters.getOptions();
    readyToProcess = true;
  }

  public static void main(String[] args) {
    BibliographicInputProcessor processor = null;
    try {
      processor = new DataElements(args);
    } catch (ParseException e) {
      System.err.println("ERROR. " + e.getLocalizedMessage());
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
  public void processRecord(BibliographicRecord marcRecord, int recordNumber) throws IOException {
    if (parameters.getRecordIgnorator().isIgnorable(marcRecord))
      return;

    printToFile(outputFile, StringUtils.join(dataElementCounter.count(marcRecord), ",") + "\n");
  }

  @Override
  public void beforeIteration() {
    logger.info(parameters.formatParameters());
    elementCardinality.put("all", new TreeMap<>());
    elementFrequency.put("all", new TreeMap<>());
    packageCounter.put("all", new TreeMap<>());
    dataElementCounter = new DataElementCounter(parameters.getOutputDir(), "top-fields.txt", DataElementCounter.Basis.EXISTENCE);
    outputFile = new File(parameters.getOutputDir(), "record-patterns.csv");
    if (outputFile.exists()) {
      try {
        Files.delete(outputFile.toPath());
      } catch (IOException e) {
        logger.log(Level.SEVERE, "The output file ({}) has not been deleted", outputFile.getAbsolutePath());
      }
    }
    printToFile(outputFile, dataElementCounter.getHeader() + "\n");
  }

  @Override
  public void fileOpened(Path file) {
    // do nothing
  }

  @Override
  public void fileProcessed() {
    // do nothing
  }

  @Override
  public void afterIteration(int numberOfprocessedRecords, long duration) {
    // do nothing
  }

  private void printToFile(File file, String message) {
    try {
      FileUtils.writeStringToFile(file, message, StandardCharsets.UTF_8, true);
    } catch (IOException e) {
      if (parameters.doLog())
        logger.log(Level.SEVERE, "printToFile", e);
    }
  }

  private char getSeparator(ValidationErrorFormat format) {
    if (format.equals(ValidationErrorFormat.TAB_SEPARATED)) {
      return '\t';
    } else {
      return ',';
    }
  }

  @Override
  public void printHelp(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    String message = String.format("java -cp qa-catalogue.jar %s [options] [file]", this.getClass().getCanonicalName());
    formatter.printHelp(message, options);
  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }
}
