package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.cli.parameters.MarcToSolrParameters;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.Leader;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
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

import static de.gwdg.metadataqa.marc.cli.CliTestUtils.getPath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MarcToSolrTest {

  @Test
  public void testVersionSpecificSubfield() {
    BibliographicRecord marcRecord = new Marc21Record("010000011");
    marcRecord.setLeader(new Leader("00860cam a22002774a 45 0"));
    marcRecord.addDataField(new DataField(Tag787.getInstance(), " ", " ","@", "japan"));
    Map<String, List<String>> solr = marcRecord.getKeyValuePairs(SolrFieldType.MIXED, false, MarcVersion.KBR);
    assertTrue(solr.containsKey("787x40_RelatedTo_language_KBR"));
    assertEquals(Arrays.asList("japan"), solr.get("787x40_RelatedTo_language_KBR"));
  }

  @Test
  public void pica() throws Exception {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getTestResource("pica/k10plus.json"));
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.PICA_PLAIN, CliTestUtils.getTestResource("pica/k10plus-sample.pica"), null);
    reader.hasNext();
    Record record = reader.next();
    BibliographicRecord marcRecord = MarcFactory.createPicaFromMarc4j(record, schema);
    Map<String, List<String>> map = marcRecord.getKeyValuePairs(SolrFieldType.HUMAN, true, MarcVersion.MARC21);
    // System.err.println(map.keySet());
    assertTrue(marcRecord.asJson().contains("036E/01"));
    assertTrue(map.containsKey("036E_01_a"));
  }

  @Test
  public void pica_extra() throws Exception {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getTestResource("pica/k10plus.json"));
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.PICA_NORMALIZED, CliTestUtils.getTestResource("pica/pica-with-holdings-info.dat"), null);
    reader.hasNext();
    Record record = reader.next();
    BibliographicRecord bibliographicRecord = MarcFactory.createPicaFromMarc4j(record, schema);

    PicaPath groupBy = PicaPathParser.parse("001@$0");
    PicaGroupIndexer groupIndexer = new PicaGroupIndexer().setPicaPath(groupBy);
    for (DataField field : bibliographicRecord.getDatafield(groupBy.getTag()))
      field.addFieldIndexer(groupIndexer);

    Map<String, List<String>> map = bibliographicRecord.getKeyValuePairs(SolrFieldType.MIXED, true, MarcVersion.MARC21);
    assertTrue(map.containsKey("001x400"));
    assertEquals(5, map.get("001x400").size());
    assertEquals("20,70,77,2035", map.get("001x400").get(0));
    assertEquals("20", map.get("001x400").get(1));
    assertEquals("70", map.get("001x400").get(2));
    assertEquals("77", map.get("001x400").get(3));
    assertEquals("2035", map.get("001x400").get(4));
  }

  @Test
  public void name() {
    assertEquals(-1, "a".compareTo("b"));
    assertEquals(0, "a".compareTo("a"));
    assertEquals(1, "b".compareTo("a"));
    assertEquals(-1, "b".compareTo("c"));
  }

  @Test
  public void run0() {
    try {
      String outputDir = getPath("src/test/resources/output");
      MarcToSolrParameters params = new MarcToSolrParameters(new String[]{
        "--schemaType", "PICA",
        "--marcFormat", "PICA_NORMALIZED",
        "--outputDir", outputDir,
        "--solrFieldType", "MIXED",
        "--useEmbedded",
        "--solrUrl", "http://localhost:8983/solr/k10plus_pica_groupped_dev",
        "--validationUrl", "http://localhost:8983/solr/k10plus_pica_groupped_validation",
        getPath("src/test/resources/pica/pica-with-holdings-info.dat")
      });
      EmbeddedSolrServer mainClient = EmbeddedSolrClientFactory.getClient(coreFromUrl(params.getSolrUrl()));
      EmbeddedSolrServer validationClient = EmbeddedSolrClientFactory.getClient(coreFromUrl(params.getValidationUrl()));
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