package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.MarcToSolrParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.datastore.MarcSolrClient;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.general.indexer.FieldIndexer;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.utils.pica.PicaGroupIndexer;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPath;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.marc4j.marc.Record;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * usage:
 * java -cp target/qa-catalogue-0.1-SNAPSHOT-jar-with-dependencies.jar de.gwdg.metadataqa.marc.cli.SolrKeyGenerator http://localhost:8983/solr/tardit 0001.0000000.formatted.json
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class MarcToSolr extends QACli<MarcToSolrParameters> implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(
    MarcToSolr.class.getCanonicalName()
  );
  private Options options;
  private MarcVersion version;
  private MarcSolrClient client;
  private MarcSolrClient validationClient;
  private Path currentFile;
  private boolean readyToProcess;
  private DecimalFormat decimalFormat = new DecimalFormat();
  private FieldIndexer groupIndexer;

  public MarcToSolr(String[] args) throws ParseException {
    parameters = new MarcToSolrParameters(args);
    initialize();
  }

  public MarcToSolr(MarcToSolrParameters parameters) throws ParseException {
    this.parameters = parameters;
    initialize();
  }

  private void initialize() {
    options = parameters.getOptions();
    client = parameters.useEmbedded()
      ? new MarcSolrClient(parameters.getMainClient())
      : new MarcSolrClient(parameters.getSolrUrl());
    client.setTrimId(parameters.getTrimId());
    client.indexWithTokenizedField(parameters.indexWithTokenizedField());
    if (parameters.getSolrForScoresUrl() != null) {
      validationClient = parameters.useEmbedded()
        ? new MarcSolrClient(parameters.getValidationClient())
        : new MarcSolrClient(parameters.getSolrForScoresUrl());
      validationClient.setTrimId(parameters.getTrimId());
    }
    readyToProcess = true;
    version = parameters.getMarcVersion();
    initializeGroups(parameters.getGroupBy(), parameters.isPica());
    if (doGroups()) {
      groupIndexer = new PicaGroupIndexer().setPicaPath((PicaPath) groupBy);
    }
  }

  public static void main(String[] args) {
    try {
      MarcToSolr processor = new MarcToSolr(args);
      processor.options.toString();
      if (StringUtils.isBlank(((MarcToSolrParameters) processor.getParameters()).getSolrUrl())) {
        System.err.println("Please provide a Solr URL and file name!");
        System.exit(1);
      }

      RecordIterator iterator = new RecordIterator(processor);
      iterator.start();
    } catch(Exception e) {
      System.err.println("ERROR. " + e.getLocalizedMessage());
      System.exit(1);
    }
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
  public void processRecord(BibliographicRecord bibliographicRecord, int recordNumber) throws IOException {
    if (parameters.getRecordIgnorator().isIgnorable(bibliographicRecord))
      return;

    if (bibliographicRecord.getSchemaType().equals(SchemaType.PICA) && doGroups())
      for (DataField groupField : bibliographicRecord.getDatafield(((PicaPath) groupBy).getTag()))
        groupField.addFieldIndexer(groupIndexer);

    Map<String, List<String>> map = bibliographicRecord.getKeyValuePairs(
      parameters.getSolrFieldType(), true, parameters.getMarcVersion()
    );
    map.put("record_sni", Arrays.asList(bibliographicRecord.asJson()));
    SolrInputDocument document = client.createSolrDoc(bibliographicRecord.getId(), map);
    if (validationClient != null) {
      SolrDocument validationValues = validationClient.get(bibliographicRecord.getId());
      if (validationValues != null && !validationValues.isEmpty())
        for (String field : validationValues.getFieldNames())
          document.addField(field, validationValues.getFieldValues(field));
    }
    client.index(document);

    if (recordNumber % parameters.getCommitAt() == 0) {
      if (parameters.doCommit())
        client.commit();
      logger.info(
        String.format(
          "%s/%s (%s)",
          currentFile.getFileName().toString(),
          decimalFormat.format(recordNumber),
          bibliographicRecord.getId()
        )
      );
    }
  }

  @Override
  public void beforeIteration() {
    logger.info(parameters.formatParameters());
    parameters.setMainClient(null);
    parameters.setValidationClient(null);
  }

  @Override
  public void fileOpened(Path path) {
    currentFile = path;
  }

  @Override
  public void fileProcessed() {

  }

  @Override
  public void afterIteration(int numberOfprocessedRecords, long duration) {
    client.commit();
    saveParameters("marctosolr.params.json", parameters, Map.of("numberOfprocessedRecords", numberOfprocessedRecords, "duration", duration));
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
