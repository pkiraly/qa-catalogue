package de.gwdg.metadataqa.marc.datastore;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarcSolrClient {

  private static final Logger logger = Logger.getLogger(MarcSolrClient.class.getCanonicalName());

  private String defaultUrl = "http://localhost:8983/solr/techproducts";
  private SolrClient solr;
  private boolean trimId = false;

  public MarcSolrClient() {
    initialize(defaultUrl);
  }

  public MarcSolrClient(String url) {
    initialize(url);
  }

  private void initialize(String url) {
    solr = new HttpSolrClient.Builder(url).build();
  }

  public void indexMap(String id, Map<String, List<String>> objectMap)
      throws IOException, SolrServerException {
    SolrInputDocument document = new SolrInputDocument();
    document.addField("id", (trimId ? id.trim() : id));
    for (Map.Entry<String, List<String>> entry : objectMap.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      if (value != null) {
        if (!key.endsWith("_sni") && !key.endsWith("_ss"))
          key += "_ss";
        document.addField(key, value);
      }
    }

    try {
      solr.add(document);
    } catch (HttpSolrClient.RemoteSolrException ex) {
      logger.log(Level.WARNING, "document", document);
      logger.log(Level.WARNING, "Commit exception", ex);
    }
  }

  public void indexDuplumKey(String id, Map<String, Object> objectMap)
      throws IOException, SolrServerException {
    SolrInputDocument document = new SolrInputDocument();
    document.addField("id", id);
    for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      if (value != null) {
        if (!key.endsWith("_sni") && !key.endsWith("_ss"))
          key += "_ss";
        document.addField(key, value);
      }
    }

    try {
      solr.add(document);
    } catch (HttpSolrClient.RemoteSolrException ex) {
      logger.log(Level.WARNING, "document", document);
      logger.log(Level.WARNING, "Commit exception", ex);
    }
  }

  public void commit() {
    try {
      solr.commit();
    } catch (IOException | SolrServerException e) {
      logger.log(Level.WARNING, "commit", e);
    }
  }

  public void optimize() {
    try {
      solr.optimize();
    } catch (IOException | SolrServerException e) {
      logger.log(Level.WARNING, "optimize", e);
    }
  }

  public boolean getTrimId() {
    return trimId;
  }

  public void setTrimId(boolean trimId) {
    this.trimId = trimId;
  }
}
