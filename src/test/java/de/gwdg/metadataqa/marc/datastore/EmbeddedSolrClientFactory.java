package de.gwdg.metadataqa.marc.datastore;

import de.gwdg.metadataqa.api.util.FileUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.NodeConfig;
import org.apache.solr.core.SolrXmlConfig;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Properties;

public class EmbeddedSolrClientFactory {

  private static EmbeddedSolrServer server;
  private EmbeddedSolrClientFactory() {}

  private EmbeddedSolrServer getServer() {
    if (server == null)
      initializeServer();
    return server;
  }

  public static void shutDown() {
    for (String coreName : server.getCoreContainer().getAllCoreNames()) {
      Path corePath = server.getCoreContainer().getCore(coreName).getInstancePath();
      try {
        org.apache.commons.io.FileUtils.deleteDirectory(corePath.toFile());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    server.getCoreContainer().shutdown();
  }

  private static void initializeServer() {
    if (server == null) {
      try {
        Path solrHomePath = FileUtils.getPath("solr-test/solr");
        NodeConfig nodeConfig = SolrXmlConfig.fromSolrHome(solrHomePath, new Properties());
        CoreContainer container = new CoreContainer(nodeConfig);
        container.load();
        server = new EmbeddedSolrServer(container, "default");
      } catch (IOException | URISyntaxException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private static void createCore(String coreName) throws SolrServerException, IOException {
    initializeServer();
    if (server.getCoreContainer().getCore(coreName) == null) {
      CoreAdminRequest.Create createRequest = new CoreAdminRequest.Create();
      createRequest.setCoreName(coreName);
      createRequest.setConfigSet("defaultConfigSet");
      server.request(createRequest);
    }
  }

  public static EmbeddedSolrServer getClient(String coreName) {
    try {
      createCore(coreName);
      return new EmbeddedSolrServer(server.getCoreContainer(), coreName);
    } catch (SolrServerException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
