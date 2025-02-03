package de.gwdg.metadataqa.marc.cli;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.TestUtils;
import de.gwdg.metadataqa.marc.cli.parameters.MarcToSolrParameters;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.Marc21Leader;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.datastore.EmbeddedSolrClientFactory;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.tags.tags76x.Tag787;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.utils.QAMarcReaderFactory;
import de.gwdg.metadataqa.marc.utils.pica.PicaGroupIndexer;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPath;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPathParser;
import org.apache.commons.cli.ParseException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MarcToSolrTest {

  @Test
  public void testVersionSpecificSubfield() {
    Marc21Record marcRecord = new Marc21BibliographicRecord("010000011");
    marcRecord.setLeader(new Marc21Leader("00860cam a22002774a 45 0"));
    marcRecord.addDataField(new DataField(Tag787.getInstance(), " ", " ","@", "japan"));
    Map<String, List<String>> solr = marcRecord.getKeyValuePairs(SolrFieldType.MIXED, false, MarcVersion.KBR);
    assertTrue(solr.containsKey("787x40_RelatedTo_language_KBR"));
    assertEquals(List.of("japan"), solr.get("787x40_RelatedTo_language_KBR"));
  }

  @Test
  public void pica() throws Exception {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getTestResource("pica/schema/k10plus.json"));
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.PICA_PLAIN, CliTestUtils.getTestResource("pica/k10plus-sample.pica"), null);
    reader.hasNext();
    Record record = reader.next();
    BibliographicRecord marcRecord = MarcFactory.createPicaFromMarc4j(record, schema);
    Map<String, List<String>> map = marcRecord.getKeyValuePairs(SolrFieldType.HUMAN, true, MarcVersion.MARC21);
    System.err.println(map.keySet());
    assertTrue(marcRecord.asJson().contains("036E/01"));
    assertTrue(map.containsKey("036E_00_09a"));
  }

  @Test
  public void pica_extra() throws Exception {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getTestResource("pica/schema/k10plus.json"));
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.PICA_NORMALIZED, CliTestUtils.getTestResource("pica/pica-with-holdings-info.dat"), null);
    reader.hasNext();
    Record record = reader.next();
    BibliographicRecord bibliographicRecord = MarcFactory.createPicaFromMarc4j(record, schema);

    PicaPath groupBy = PicaPathParser.parse("001@$0");
    PicaGroupIndexer groupIndexer = new PicaGroupIndexer().setPicaPath(groupBy);
    for (DataField field : bibliographicRecord.getDatafieldsByTag(groupBy.getTag()))
      field.addFieldIndexer(groupIndexer);

    Map<String, List<String>> map = bibliographicRecord.getKeyValuePairs(SolrFieldType.MIXED, true, MarcVersion.MARC21);
    System.err.println(map.keySet());
    assertTrue(map.containsKey("001_0"));
    assertEquals(5, map.get("001_0").size());
    assertEquals("20,70,77,2035", map.get("001_0").get(0));
    assertEquals("20", map.get("001_0").get(1));
    assertEquals("70", map.get("001_0").get(2));
    assertEquals("77", map.get("001_0").get(3));
    assertEquals("2035", map.get("001_0").get(4));
  }

  @Test
  public void runEmbeddedSolr() {
    try {
      String outputDir = TestUtils.getPath("output");
      MarcToSolrParameters params = new MarcToSolrParameters(new String[]{
        "--schemaType", "PICA",
        "--marcFormat", "PICA_NORMALIZED",
        "--outputDir", outputDir,
        "--solrFieldType", "MIXED",
        "--useEmbedded",
        "--solrUrl", "http://localhost:8983/solr/k10plus_pica_grouped_dev",
        "--solrForScoresUrl", "http://localhost:8983/solr/k10plus_pica_grouped_scores",
        TestUtils.getPath("pica/pica-with-holdings-info.dat")
      });
      EmbeddedSolrServer mainClient = EmbeddedSolrClientFactory.getClient(coreFromUrl(params.getSolrUrl()));
      EmbeddedSolrServer validationClient = EmbeddedSolrClientFactory.getClient(coreFromUrl(params.getSolrForScoresUrl()));
      params.setMainClient(mainClient);
      params.setValidationClient(validationClient);

      Map<String, SolrInputDocument> validation = new HashMap<>();
      List<String> ids = List.of("010000011", "01000002X", "010000038", "010000054", "010000070", "010000089", "010000127", "010000151", "010000178", "010000194");
      for (String id : ids) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", id);
        document.addField("groupId_is", getNRandomNumbers(5, 1, 10));
        document.addField("errorId_is", getNRandomNumbers(5, 10, 20));
        validation.put(id, document);
        validationClient.add(document);
      }
      validationClient.commit();

      MarcToSolr processor = new MarcToSolr(params);
      RecordIterator iterator = new RecordIterator(processor);
      iterator.start();
      assertEquals("done", iterator.getStatus());

      final QueryResponse response = mainClient.query(new MapSolrParams(Map.of("q", "*:*")));
      final SolrDocumentList documents = response.getResults();
      assertNotNull(documents);
      assertEquals(10, documents.getNumFound());
      for (SolrDocument doc : documents) {
        SolrInputDocument intention = validation.get(doc.get("id"));
        assertEquals(intention.getFieldValues("groupId_is"), doc.getFieldValues("groupId_is"));
      }
    } catch (ParseException | SolrServerException | IOException e) {
      throw new RuntimeException(e);
    }

    EmbeddedSolrClientFactory.shutDown();
  }

  @Test
  public void runEmbeddedSolr_with_fieldPrefix() {
    try {
      String outputDir = TestUtils.getPath("output");
      MarcToSolrParameters params = new MarcToSolrParameters(new String[]{
        "--schemaType", "PICA",
        "--marcFormat", "PICA_NORMALIZED",
        "--outputDir", outputDir,
        "--solrFieldType", "MIXED",
        "--fieldPrefix", "q",
        "--useEmbedded",
        "--solrUrl", "http://localhost:8983/solr/k10plus_pica_grouped_dev",
        "--solrForScoresUrl", "http://localhost:8983/solr/k10plus_pica_grouped_scores",
        TestUtils.getPath("pica/pica-with-holdings-info.dat")
      });
      EmbeddedSolrServer mainClient = EmbeddedSolrClientFactory.getClient(coreFromUrl(params.getSolrUrl()));
      EmbeddedSolrServer validationClient = EmbeddedSolrClientFactory.getClient(coreFromUrl(params.getSolrForScoresUrl()));
      params.setMainClient(mainClient);
      params.setValidationClient(validationClient);

      Map<String, SolrInputDocument> validation = new HashMap<>();
      List<String> ids = List.of("010000011", "01000002X", "010000038", "010000054", "010000070", "010000089", "010000127", "010000151", "010000178", "010000194");
      for (String id : ids) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", id);
        document.addField("groupId_is", getNRandomNumbers(5, 1, 10));
        document.addField("errorId_is", getNRandomNumbers(5, 10, 20));
        validation.put(id, document);
        validationClient.add(document);
      }
      validationClient.commit();

      MarcToSolr processor = new MarcToSolr(params);
      RecordIterator iterator = new RecordIterator(processor);
      iterator.start();
      assertEquals("done", iterator.getStatus());

      final QueryResponse response = mainClient.query(new MapSolrParams(Map.of("q", "*:*")));
      final SolrDocumentList documents = response.getResults();
      assertNotNull(documents);
      assertEquals(10, documents.getNumFound());
      for (SolrDocument doc : documents) {
        SolrInputDocument intention = validation.get(doc.get("id"));
        assertEquals(intention.getFieldValues("groupId_is"), doc.getFieldValues("groupId_is"));
        for (String fieldName : doc.getFieldNames()) {
          if (!fieldName.equals("id") && !fieldName.endsWith("_sni") && !fieldName.endsWith("_is")) {
            assertTrue(
              String.format("fieldName '%s' should start with '%s'", fieldName, params.getFieldPrefix()),
              fieldName.startsWith(params.getFieldPrefix())
            );
          }
        }
      }
    } catch (ParseException | SolrServerException | IOException e) {
      throw new RuntimeException(e);
    }

    EmbeddedSolrClientFactory.shutDown();
  }

  @Test
  public void unimarc_withEmbeddedSolr() {
    try {
      String outputDir = TestUtils.getPath("output");
      MarcToSolrParameters params = new MarcToSolrParameters(new String[]{
        "--schemaType", "UNIMARC",
        "--outputDir", outputDir,
        "--solrFieldType", "human-readable",
        "--useEmbedded",
        "--solrUrl", "http://localhost:8983/solr/unimarc",
        TestUtils.getPath("unimarc/serial.bnr.1993.mrc")
      });

      EmbeddedSolrServer mainClient = EmbeddedSolrClientFactory.getClient(coreFromUrl(params.getSolrUrl()));
      params.setMainClient(mainClient);

      MarcToSolr processor = new MarcToSolr(params);
      RecordIterator iterator = new RecordIterator(processor);
      iterator.start();
      assertEquals("done", iterator.getStatus());

      final QueryResponse response = mainClient.query(new MapSolrParams(Map.of("q", "*:*")));
      final SolrDocumentList documents = response.getResults();
      assertNotNull(documents);
      assertEquals(11, documents.getNumFound());
    } catch (ParseException | SolrServerException | IOException e) {
      throw new RuntimeException(e);
    }

    EmbeddedSolrClientFactory.shutDown();
  }

  @Test
  public void jsonParameters() throws ParseException, JsonProcessingException {
    String outputDir = TestUtils.getPath("output");
    MarcToSolrParameters parameters = new MarcToSolrParameters(new String[]{
      "--schemaType", "PICA",
      "--marcFormat", "PICA_NORMALIZED",
      "--outputDir", outputDir,
      "--solrFieldType", "MIXED",
      "--useEmbedded",
      "--solrUrl", "http://localhost:8983/solr/k10plus_pica_grouped_dev",
      "--solrForScoresUrl", "http://localhost:8983/solr/k10plus_pica_grouped_scores",
      "--indexFieldCounts",
      TestUtils.getPath("pica/pica-with-holdings-info.dat")
    });
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(parameters);
    System.err.println(json);
  }

  /**
   * Get a list of randum numbers
   * @param n The number of numbers to retrieve
   * @param min Minimum value
   * @param max Maximum value
   * @return List of random numbers
   */
  private Object getNRandomNumbers(int n, int min, int max) {
    List<Integer> numbers = new ArrayList<>();
    for (int i = 0; i < n; i++)
      numbers.add(ThreadLocalRandom.current().nextInt(min, max + 1));
    return numbers;
  }

  private String coreFromUrl(String url) {
    return url.substring(url.lastIndexOf("/") + 1);
  }
}