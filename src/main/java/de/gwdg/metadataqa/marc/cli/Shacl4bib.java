package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.configuration.ConfigurationReader;
import de.gwdg.metadataqa.api.configuration.SchemaConfiguration;
import de.gwdg.metadataqa.api.interfaces.MetricResult;
import de.gwdg.metadataqa.api.rule.RuleCatalog;
import de.gwdg.metadataqa.api.schema.BaseSchema;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.Shacl4bibParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.utils.marcspec.legacy.MarcSpec;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.model.pathcache.BasePathCache;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.marc4j.marc.Record;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Shacl4bib implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(Shacl4bib.class.getCanonicalName());
  private final boolean readyToProcess;

  private Shacl4bibParameters parameters;

  //private MarcSpec marcPointer;

  private List<String> tagsList = new ArrayList();

  private File outPut;
  private RuleCatalog ruleCatalog;

  private boolean fileOpenPointer = false;

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

    logger.info(parameters.formatParameters());
    outPut = new File(parameters.getOutputDir());
    // 1- open and read the configurations file

    String fileName = "de/gwdg/metadataqa/marc/cli/Schema-Configuration.json";
    SchemaConfiguration schema = null;
    try {
      schema = ConfigurationReader.readSchemaJson(fileName); //Paths.get
    } catch (IOException exception) {
      System.err.println("ERROR. " + exception.getLocalizedMessage());
      System.exit(0);
    }
    // 2- process the SHACL Rules of the configurations file
    this.ruleCatalog = new RuleCatalog(schema.asSchema());



  }

  @Override
  public void fileOpened(Path path) {
    logger.info("file opened: " + path);
    fileOpenPointer = true;
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {

  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber) throws IOException {
    List<MetricResult> results = ruleCatalog.measure(new MarcPathCache(marcRecord));
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
