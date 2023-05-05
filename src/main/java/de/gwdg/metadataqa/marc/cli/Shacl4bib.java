package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.calculator.output.MetricCollector;
import de.gwdg.metadataqa.api.calculator.output.OutputCollector;
import de.gwdg.metadataqa.api.configuration.ConfigurationReader;
import de.gwdg.metadataqa.api.configuration.SchemaConfiguration;
import de.gwdg.metadataqa.api.interfaces.MetricResult;
import de.gwdg.metadataqa.api.rule.RuleCatalog;
import de.gwdg.metadataqa.api.util.CompressionLevel;
import de.gwdg.metadataqa.marc.CsvUtils;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.Shacl4bibParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.BibSelector;
import de.gwdg.metadataqa.marc.cli.utils.BibSelectorFactory;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.marc4j.marc.Record;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Shacl4bib extends QACli implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(Shacl4bib.class.getCanonicalName());
  private final boolean readyToProcess;

  private Shacl4bibParameters parameters;
  private File outputFile;
  private RuleCatalog ruleCatalog;
  private SchemaConfiguration schema;

  public Shacl4bib(String[] args) throws ParseException {
    parameters = new Shacl4bibParameters(args);
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
    outputFile = new File(parameters.getOutputDir(), parameters.getShaclOutputFile());

    String shaclConfigurationFile = parameters.getShaclConfigurationFile();
    try {
      if (shaclConfigurationFile.endsWith(".json"))
        schema = ConfigurationReader.readSchemaJson(shaclConfigurationFile);
      else
        schema = ConfigurationReader.readSchemaYaml(shaclConfigurationFile);
    } catch (IOException exception) {
      logger.severe("Error when the SHACL schema is initialized. " + exception.getLocalizedMessage());
      System.exit(0);
    }

    this.ruleCatalog = new RuleCatalog(schema.asSchema())
      .setOnlyIdInHeader(true)
      .setOutputType(parameters.getShaclOutputType());

    List<String> header = ruleCatalog.getHeader();
    header.add(0, "id");
    printToFile(outputFile, CsvUtils.createCsv(header));
  }

  @Override
  public void fileOpened(Path path) {
    logger.log(Level.INFO, "file opened: {0}", path);
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {

  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber) throws IOException {
    BibSelector selector = BibSelectorFactory.create(schema.getFormat(), marcRecord);

    if (selector != null) {
      List<MetricResult> results = ruleCatalog.measure(selector);
      MetricCollector collector = new MetricCollector();
      collector.addResult(ruleCatalog, results, CompressionLevel.NORMAL);
      List<Object> values = (List<Object>) collector.createOutput(OutputCollector.TYPE.STRING_LIST, CompressionLevel.NORMAL);
      values.add(0, marcRecord.getId(true));
      printToFile(outputFile, CsvUtils.createCsvFromObjects(values));
    }
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
