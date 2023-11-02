package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.analysis.BLClassifier;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.marc4j.marc.Record;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.gwdg.metadataqa.marc.Utils.createRow;

public class BLClassificationAnalysis extends QACli<CommonParameters> implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(
    BLClassificationAnalysis.class.getCanonicalName()
  );
  public static final String BL_CLASSIFIER_FILE = "bl-classifier.csv";
  private final Options options;
  private final boolean readyToProcess;
  private File output = null;
  private BLClassifier classifier = null;

  public BLClassificationAnalysis(String[] args) throws ParseException {
    parameters = new CommonParameters(args);
    options = parameters.getOptions();
    classifier = new BLClassifier();
    readyToProcess = true;
  }

  public static void main(String[] args) {
    BibliographicInputProcessor processor = null;
    try {
      processor = new BLClassificationAnalysis(args);
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
  public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {
    // do nothing
  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber, List<ValidationError> errors) throws IOException {
    // do nothing
  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber) throws IOException {
    logger.info(".");
    if (parameters.getRecordIgnorator().isIgnorable(marcRecord))
      return;
    String blClass = classifier.classify(marcRecord);
    String id = parameters.getTrimId()
      ? marcRecord.getId().trim()
      : marcRecord.getId();

    print(createRow(id, blClass));
  }

  private void print(String message) {
    try {
      FileUtils.writeStringToFile(output, message, Charset.defaultCharset(), true);
    } catch (IOException e) {
      logger.log(Level.WARNING, "print", e);
    }
  }

  @Override
  public void beforeIteration() {
    logger.info(parameters.formatParameters());
    // printFields();

    output = new File(parameters.getOutputDir(), BL_CLASSIFIER_FILE);
    if (output.exists() && !output.delete())
      logger.severe("Deletion of " + output.getAbsolutePath() + " was unsuccessful!");
  }

  @Override
  public void fileOpened(Path path) {
    logger.info("file opened: " + path);
  }

  @Override
  public void fileProcessed() {
    logger.info("file processed");
  }

  @Override
  public void afterIteration(int numberOfprocessedRecords, long duration) {
    saveParameters("bl-classifications.params.json", parameters, Map.of("numberOfprocessedRecords", numberOfprocessedRecords, "duration", duration));
    logger.info("after iteration: " + numberOfprocessedRecords);
  }

  @Override
  public void printHelp(Options options) {

  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }
}
