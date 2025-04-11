package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.configuration.SchemaConfiguration;
import de.gwdg.metadataqa.api.rule.RuleCatalog;
import de.gwdg.metadataqa.marc.CsvUtils;
import de.gwdg.metadataqa.marc.RuleCatalogUtils;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.Shacl4bibParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.BibSelector;
import de.gwdg.metadataqa.marc.cli.utils.BibSelectorFactory;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.cli.utils.ShaclUtils;
import de.gwdg.metadataqa.marc.cli.utils.ignorablerecords.RecordFilter;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.marc4j.marc.Record;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Shacl4bib extends QACli<Shacl4bibParameters>
                       implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(Shacl4bib.class.getCanonicalName());

  private final boolean readyToProcess;
  private File outputFile;
  private RuleCatalog ruleCatalog;
  private SchemaConfiguration schema;
  private RecordFilter recordFilter;

  public Shacl4bib(String[] args) throws ParseException {
    parameters = new Shacl4bibParameters(args);
    recordFilter = parameters.getRecordFilter();
    readyToProcess = true;
  }

  public static void main(String[] args) {
    BibliographicInputProcessor processor = null;
    try {
      processor = new Shacl4bib(args);
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
    iterator.setProcessWithErrors(processor.getParameters().getProcessRecordsWithoutId());
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

    schema = ShaclUtils.setupSchema(parameters);
    ruleCatalog = ShaclUtils.setupRuleCatalog(schema, parameters);

    if (outputFile.exists()) {
      try {
        Files.delete(outputFile.toPath());
      } catch (IOException e) {
        logger.log(Level.SEVERE, "The output file ({}) has not been deleted", outputFile.getAbsolutePath());
      }
    }
    List<String> header = ruleCatalog.getHeader();
    header.add(0, "id");
    printToFile(outputFile, CsvUtils.createCsv(header));
  }

  @Override
  public void fileOpened(Path path) {
    logger.log(Level.INFO, "file opened: {0}", new Object[]{path});
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {
    // do nothing
  }

  @Override
  public void processRecord(BibliographicRecord bibliographicRecord, int recordNumber, List<ValidationError> errors) throws IOException {
    processRecord(bibliographicRecord, recordNumber);
  }

  @Override
  public void processRecord(BibliographicRecord bibliographicRecord, int recordNumber) throws IOException {
    if (!recordFilter.isAllowable(bibliographicRecord)) {
      logger.info("ignoring " + bibliographicRecord.getId());
      return;
    }

    BibSelector selector = BibSelectorFactory.create(schema.getFormat(), bibliographicRecord);

    if (selector != null) {
      List<Object> values = RuleCatalogUtils.extract(ruleCatalog, ruleCatalog.measure(selector));
      values.add(0, bibliographicRecord.getId(true));
      printToFile(outputFile, CsvUtils.createCsvFromObjects(values));
    }
  }

  @Override
  public void fileProcessed() {
    // do nothing
  }

  @Override
  public void afterIteration(int numberOfprocessedRecords, long duration) {
    copyFileToOutputDir(parameters.getShaclConfigurationFile());
    saveParameters("shacl4bib.params.json", parameters, Map.of("numberOfprocessedRecords", numberOfprocessedRecords, "duration", duration));
  }

  @Override
  public void printHelp(Options options) {

  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }
}
