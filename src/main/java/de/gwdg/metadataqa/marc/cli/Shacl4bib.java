package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.Shacl4bibParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.marc4j.marc.Record;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.logging.Logger;

public class Shacl4bib implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(Shacl4bib.class.getCanonicalName());
  private final boolean readyToProcess;

  private Shacl4bibParameters parameters;

  public Shacl4bib(String[] args) throws ParseException {
    parameters = new Shacl4bibParameters(args);
    this.parameters = parameters;
    readyToProcess = true;
  }

  public static void main(String[] args) {
    BibliographicInputProcessor processor = null;
    try {
      processor = new Shacl4bib(args);
    } catch (ParseException e) {
      System.err.println("ERROR. " + e.getLocalizedMessage());
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

  @Override
  public CommonParameters getParameters() {
    return parameters;
  }

  @Override
  public void beforeIteration() {

  }

  @Override
  public void fileOpened(Path path) {

  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {

  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber) throws IOException {

  }

  @Override
  public void fileProcessed() {

  }

  @Override
  public void afterIteration(int numberOfprocessedRecords) {

  }

  @Override
  public void printHelp(Options options) {

  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }
}
