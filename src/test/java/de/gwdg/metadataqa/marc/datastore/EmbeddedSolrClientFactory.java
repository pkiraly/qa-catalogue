package de.gwdg.metadataqa.marc.datastore;

import de.gwdg.metadataqa.api.util.FileUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.common.params.CoreAdminParams;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.NodeConfig;
import org.apache.solr.core.SolrXmlConfig;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmbeddedSolrClientFactory {

  private static final Logger logger = Logger.getLogger(EmbeddedSolrClientFactory.class.getCanonicalName());

  private static EmbeddedSolrServer server;
  private EmbeddedSolrClientFactory() {}

  private EmbeddedSolrServer getServer() {
    if (server == null)
      initializeServer();
    return server;
  }

  public static void shutDown() {
    CoreContainer container = server.getCoreContainer();
    List<String> cores = new ArrayList<>();
    for (String coreName : container.getAllCoreNames()) {
      logger.log(Level.INFO, "Delete container '{0}' located at {1}", new String[]{coreName, container.getCore(coreName).getInstancePath().toString()});
      cores.add(coreName);
    }

    container.shutdown();
    for (String coreName : cores) {
      container.unload(coreName, true, true, true);
    }
    server = null;
  }

  private static void initializeServer() {
    if (server != null) {
      return;
    }

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

  private static void createCore(String coreName) throws SolrServerException, IOException {
    initializeServer();
    if (server.getCoreContainer().getCore(coreName) != null) {
      return;
    }
    CoreAdminRequest.Create createRequest = new CoreAdminRequest.Create();
    createRequest.setCoreName(coreName);
    createRequest.setConfigSet("defaultConfigSet");
    server.request(createRequest);
  }

  private static void reload() throws SolrServerException, IOException {
    for (String coreName : server.getCoreContainer().getAllCoreNames()) {
      CoreAdminRequest req = new CoreAdminRequest();
      req.setCoreName(coreName);
      req.setAction(CoreAdminParams.CoreAdminAction.RELOAD);
      req.process(server, coreName);
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
