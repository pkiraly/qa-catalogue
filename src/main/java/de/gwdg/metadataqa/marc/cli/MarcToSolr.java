package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.MarcToSolrParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.datastore.MarcSolrClient;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.general.indexer.FieldIndexer;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.utils.Counter;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;
import de.gwdg.metadataqa.marc.utils.pica.PicaFieldDefinition;
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
import java.util.Collections;
import java.util.HashMap;
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
  private MarcSolrClient client;
  private MarcSolrClient validationClient;
  private Path currentFile;
  private boolean readyToProcess;
  private final DecimalFormat decimalFormat = new DecimalFormat();
  private FieldIndexer groupIndexer;
  private final Map<String, String> escapedTagCache = new HashMap<>();

  public MarcToSolr(String[] args) throws ParseException {
    parameters = new MarcToSolrParameters(args);
    initialize();
  }

  public MarcToSolr(MarcToSolrParameters parameters) {
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

    if (parameters.getFieldPrefix() != null) {
      client.setFieldPrefix(parameters.getFieldPrefix());
    }

    if (parameters.getSolrForScoresUrl() != null) {
      validationClient = parameters.useEmbedded()
        ? new MarcSolrClient(parameters.getValidationClient())
        : new MarcSolrClient(parameters.getSolrForScoresUrl());
      validationClient.setTrimId(parameters.getTrimId());

      if (parameters.getFieldPrefix() != null) {
        validationClient.setFieldPrefix(parameters.getFieldPrefix());
      }
    }

    readyToProcess = true;
    initializeGroups(parameters.getGroupBy(), parameters.isPica());
    if (doGroups()) {
      groupIndexer = new PicaGroupIndexer().setPicaPath((PicaPath) groupBy);
    }
  }

  public static void main(String[] args) {
    try {
      MarcToSolr processor = new MarcToSolr(args);
      if (StringUtils.isBlank(((MarcToSolrParameters) processor.getParameters()).getSolrUrl())) {
        logger.severe("Please provide a Solr URL and file name!");
        System.exit(1);
      }

      RecordIterator iterator = new RecordIterator(processor);
      iterator.start();
      System.exit(0);
    } catch(Exception e) {
      logger.severe(() -> "ERROR. " + e.getLocalizedMessage());
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

    if (bibliographicRecord.getSchemaType().equals(SchemaType.PICA) && doGroups()) {
      for (DataField groupField : bibliographicRecord.getDatafieldsByTag(((PicaPath) groupBy).getTag())) {
        groupField.addFieldIndexer(groupIndexer);
      }
    }

    Map<String, List<String>> keyValuePairs = bibliographicRecord.getKeyValuePairs(
      parameters.getSolrFieldType(), true, parameters.getMarcVersion()
    );

    // Add the record itself as a field to the index
    keyValuePairs.put("record_sni", Collections.singletonList(bibliographicRecord.asJson()));

    SolrInputDocument solrDocument = client.createSolrDoc(bibliographicRecord.getId(), keyValuePairs);
    if (validationClient != null) {
      indexValidationResults(bibliographicRecord, solrDocument);
    }

    if (parameters.indexFieldCounts()) {
      indexFieldCounts(bibliographicRecord, solrDocument);
    }

    client.index(solrDocument);

    if (recordNumber % parameters.getCommitAt() != 0) {
      return;
    }

    if (parameters.doCommit()) {
      client.commit();
    }

    String logMessage = String.format(
      "%s/%s (%s)",
      currentFile.getFileName().toString(),
      decimalFormat.format(recordNumber),
      bibliographicRecord.getId()
    );
    logger.info(logMessage);
  }

  private void indexValidationResults(BibliographicRecord bibliographicRecord, SolrInputDocument document) {
    SolrDocument validationValues = validationClient.get(bibliographicRecord.getId());
    if (validationValues == null || validationValues.isEmpty()) {
      return;
    }

    for (String field : validationValues.getFieldNames()) {
      document.addField(field, validationValues.getFieldValues(field));
    }
  }

  private void indexFieldCounts(BibliographicRecord bibliographicRecord, SolrInputDocument document) {
    Counter<String> fieldCounter = new Counter<>();
    boolean isPica = bibliographicRecord.getSchemaType().equals(SchemaType.PICA);
    for (DataField field : bibliographicRecord.getDatafields()) {
      String tag;
      if (field.getDefinition() != null) {
        tag = isPica
          ? ((PicaFieldDefinition)field.getDefinition()).getId()
          : field.getDefinition().getTag();
      } else {
        tag = field.getTag();
      }
      String safeTag = escape(tag);
      fieldCounter.count(safeTag);
    }
    for (Map.Entry<String, Integer> entry : fieldCounter.entrySet()) {
      document.addField(String.format("%s_count_i", entry.getKey()), entry.getValue());
    }
  }

  private String escape(String tag) {
    escapedTagCache.putIfAbsent(tag, DataFieldKeyGenerator.escape(tag));
    return escapedTagCache.get(tag);
  }

  @Override
  public void beforeIteration() {
    logger.info(() -> parameters.formatParameters());
    parameters.setMainClient(null);
    parameters.setValidationClient(null);
  }

  @Override
  public void fileOpened(Path path) {
    currentFile = path;
  }

  @Override
  public void fileProcessed() {
    // Do nothing
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
