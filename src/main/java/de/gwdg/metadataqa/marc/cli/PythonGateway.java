package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.processor.MinimalProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.marc4j.marc.Record;
import py4j.GatewayServer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

public class PythonGateway implements BibliographicInputProcessor {
  private static final Logger logger = Logger.getLogger(PythonGateway.class.getCanonicalName());
  MinimalProcessor processor;
  CommonParameters parameters;

  public PythonGateway()  {
    try {
      parameters = new CommonParameters(new String[]{});
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public void setProcessor(MinimalProcessor processor) {
    logger.info("setProcessor");
    this.processor = processor;
  }

  public void setParameters(String args) {
    logger.info("setParameters");
    logger.info("args: " + args);
    try {
      parameters = new CommonParameters(args.split(" "));
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public void start() {
    RecordIterator iterator = new RecordIterator(this);
    iterator.setProcessWithErrors(true);
    iterator.start();
  }

  public static void main(String[] args) {
    logger.info("Welcome to PythonGateway of QA Catalogue!");
    // ListenerApplication application = new ListenerApplication();
    // GatewayServer server = new GatewayServer(application);

    // iterator.setProcessWithErrors(processor.getParameters().getProcessRecordsWithoutId());
    PythonGateway gateway = new PythonGateway();
    GatewayServer server = new GatewayServer(gateway);
    server.start(true);
    logger.info("The server is ready to process requests.");
  }

  @Override
  public CommonParameters getParameters() {
    return parameters;
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {
  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber) throws IOException {
  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber, List<ValidationError> errors) throws IOException {
    processor.processRecord(marcRecord, recordNumber);
  }

  @Override
  public void beforeIteration() {
    processor.beforeIteration();
  }

  @Override
  public void fileOpened(Path path) {
    processor.fileOpened(path);
  }

  @Override
  public void fileProcessed() {
    processor.fileProcessed();
  }

  @Override
  public void afterIteration(int numberOfprocessedRecords, long duration) {
    processor.afterIteration(numberOfprocessedRecords, duration);
  }

  @Override
  public void printHelp(Options options) {
    processor.printHelp(options);
  }

  @Override
  public boolean readyToProcess() {
    return processor.readyToProcess();
  }
}
