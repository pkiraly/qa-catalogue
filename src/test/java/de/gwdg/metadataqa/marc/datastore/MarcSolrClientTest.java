package de.gwdg.metadataqa.marc.datastore;

import de.gwdg.metadataqa.api.util.FileUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.core.NodeConfig;
import org.apache.solr.core.SolrXmlConfig;
import org.junit.After;
import org.junit.Before;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MarcSolrClientTest {

  static EmbeddedSolrServer server;
  static List<String> cores = List.of("qa_catalogue", "qa_catalogue_validation");

  @Before
  public void setUp() throws Exception {
    setupSolrServer();
    insertValidationData();
  }

  private void insertValidationData() {
    EmbeddedSolrServer client = new EmbeddedSolrServer(server.getCoreContainer(), "qa_catalogue_validation");
    SolrInputDocument document = new SolrInputDocument();
    document.addField("id", "123");
    document.addField("groupId_is", List.of(1, 2, 3));
    document.addField("errorId_is", List.of(11, 12, 13));
    try {
      client.add(document);
      client.commit();
    } catch (SolrServerException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void setupSolrServer() throws IOException, SolrServerException {
    try {
      Path solrHomePath = FileUtils.getPath("solr-test/solr");
      NodeConfig nodeConfig = SolrXmlConfig.fromSolrHome(solrHomePath, new Properties());
      CoreContainer container = new CoreContainer(nodeConfig);
      container.load();
      server = new EmbeddedSolrServer(container, "default");

      for (String coreName : cores)
        createCore(coreName);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  private static void createCore(String coreName) throws SolrServerException, IOException {
    if (server.getCoreContainer().getCore(coreName) == null) {
      CoreAdminRequest.Create createRequest = new CoreAdminRequest.Create();
      createRequest.setCoreName(coreName);
      createRequest.setConfigSet("defaultConfigSet");
      server.request(createRequest);
    }
  }

  @After
  public void tearDown() throws Exception {
    for (String coreName : cores) {
      Path corePath = server.getCoreContainer().getCore(coreName).getInstancePath();
      org.apache.commons.io.FileUtils.deleteDirectory(corePath.toFile());
    }

    server.getCoreContainer().shutdown();
  }

  @Test
  public void testValidationData() throws SolrServerException, IOException {
    EmbeddedSolrServer client = new EmbeddedSolrServer(server.getCoreContainer(), "qa_catalogue_validation");
    final QueryResponse response = client.query(new MapSolrParams(Map.of("q", String.format("id:\"%s\"", 123))));
    final SolrDocumentList documents = response.getResults();
    assertNotNull(documents);
    assertEquals(1, documents.getNumFound());
    SolrDocument doc = documents.get(0);
    assertEquals(Set.of("id", "groupId_is", "errorId_is"), doc.getFieldNames());
    assertEquals("123", doc.getFieldValue("id"));
    assertEquals(List.of(1, 2, 3), doc.getFieldValues("groupId_is"));
    assertEquals(List.of(11, 12, 13), doc.getFieldValues("errorId_is"));
  }

  @Test
  public void get() throws SolrServerException, IOException {
    EmbeddedSolrServer client = new EmbeddedSolrServer(server.getCoreContainer(), "qa_catalogue_validation");
    MarcSolrClient marcSolrClient = new MarcSolrClient(client);
    final QueryResponse response = client.query(new MapSolrParams(Map.of("q", String.format("id:\"%s\"", 123))));
    final SolrDocument doc = marcSolrClient.get("123");
    assertEquals(Set.of("groupId_is", "errorId_is"), doc.getFieldNames());
    assertEquals(List.of(1, 2, 3), doc.getFieldValues("groupId_is"));
    assertEquals(List.of(11, 12, 13), doc.getFieldValues("errorId_is"));
  }

  @Test
  public void indexMap() throws SolrServerException, IOException {
    MarcSolrClient mainClient = new MarcSolrClient(new EmbeddedSolrServer(server.getCoreContainer(), "qa_catalogue"));
    mainClient.indexWithTokenizedField(true);
    mainClient.indexMap("124", Map.of("title_ss", List.of("Hello world")));
    mainClient.commit();

    assertNull(mainClient.get("123"));

    final SolrDocument doc = mainClient.get("124");
    assertEquals(Set.of("title_ss"), doc.getFieldNames());
    assertEquals(List.of("Hello world"), doc.getFieldValues("title_ss"));
  }

  @Test
  public void createSolrDoc() throws SolrServerException, IOException {
    MarcSolrClient mainClient = new MarcSolrClient(new EmbeddedSolrServer(server.getCoreContainer(), "qa_catalogue"));
    mainClient.indexWithTokenizedField(true);
    SolrInputDocument doc = mainClient.createSolrDoc("124", Map.of("title_ss", List.of("Hello world")));

    assertEquals(Set.of("id", "title_ss", "title_tt"), doc.getFieldNames());
    assertEquals(List.of("Hello world"), doc.getFieldValues("title_ss"));
  }

  @Test
  public void merge() throws SolrServerException, IOException {
    MarcSolrClient mainClient = new MarcSolrClient(new EmbeddedSolrServer(server.getCoreContainer(), "qa_catalogue"));
    mainClient.indexWithTokenizedField(true);
    MarcSolrClient validationClient = new MarcSolrClient(new EmbeddedSolrServer(server.getCoreContainer(), "qa_catalogue_validation"));

    SolrInputDocument doc = mainClient.createSolrDoc("123", Map.of("title_ss", List.of("Hello world")));

    SolrDocument validationValues = validationClient.get(doc.getFieldValue("id").toString());
    if (validationValues != null && !validationValues.isEmpty())
      for (String field : validationValues.getFieldNames())
        doc.addField(field, validationValues.getFieldValues(field));

    assertEquals(Set.of("id", "title_ss", "title_tt", "groupId_is", "errorId_is"), doc.getFieldNames());
    assertEquals(List.of("Hello world"), doc.getFieldValues("title_ss"));
    assertEquals(List.of(1, 2, 3), doc.getFieldValues("groupId_is"));
    assertEquals(List.of(11, 12, 13), doc.getFieldValues("errorId_is"));
  }

  @Test
  public void merge_withCommit() throws SolrServerException, IOException {
    MarcSolrClient mainClient = new MarcSolrClient(new EmbeddedSolrServer(server.getCoreContainer(), "qa_catalogue"));
    mainClient.indexWithTokenizedField(true);
    MarcSolrClient validationClient = new MarcSolrClient(new EmbeddedSolrServer(server.getCoreContainer(), "qa_catalogue_validation"));

    SolrInputDocument doc = mainClient.createSolrDoc("123", Map.of("title_ss", List.of("Hello world")));
    SolrDocument validationValues = validationClient.get("123");
    if (validationValues != null && !validationValues.isEmpty())
      for (String field : validationValues.getFieldNames())
        doc.addField(field, validationValues.getFieldValues(field));
    mainClient.index(doc);
    mainClient.commit();

    SolrDocument savedValues = mainClient.get("123");
    assertEquals(Set.of("title_ss", "groupId_is", "errorId_is"), savedValues.getFieldNames());
    assertEquals(List.of("Hello world"), savedValues.getFieldValues("title_ss"));
    assertEquals(List.of(1, 2, 3), savedValues.getFieldValues("groupId_is"));
    assertEquals(List.of(11, 12, 13), savedValues.getFieldValues("errorId_is"));
  }

  @Test
  public void deleteAll() throws SolrServerException, IOException {
    EmbeddedSolrServer client = new EmbeddedSolrServer(server.getCoreContainer(), "qa_catalogue");

    UpdateRequest up = new UpdateRequest();
    up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
    up.deleteByQuery("*:*");
    up.process(client);
    up.clear();

    final QueryResponse response = client.query(new MapSolrParams(Map.of("q", "*:*")));
    final SolrDocumentList documents = response.getResults();
    assertNotNull(documents);
    assertEquals(0, documents.getNumFound());
  }
}