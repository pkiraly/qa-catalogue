package de.gwdg.metadataqa.marc.datastore;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarcSolrClient {

  private static final Logger logger = Logger.getLogger(MarcSolrClient.class.getCanonicalName());
  public static final String ID_QUERY = "id:\"%s\"";

  private String defaultUrl = "http://localhost:8983/solr";
  private SolrClient solrClient;
  private boolean trimId = false;
  private boolean indexWithTokenizedField = false;
  private String termFieldSuffix = "_tt";
  private String fieldPrefix = "";
  private Map<String, String> termFieldNameCache = new HashMap<>();

  public MarcSolrClient() {
    initialize(defaultUrl);
  }

  public MarcSolrClient(String url) {
    initialize(url);
  }

  public MarcSolrClient(SolrClient client) {
    solrClient = client;
  }

  private void initialize(String url) {
    solrClient = new HttpSolrClient.Builder(url).build();
  }

  public void indexMap(String id, Map<String, List<String>> objectMap) {
    index(createSolrDoc(id, objectMap));
  }

  public void index(SolrInputDocument document) {
    try {
      solrClient.add(document);
    } catch (HttpSolrClient.RemoteSolrException | SolrServerException | IOException ex) {
      logger.log(Level.WARNING, "document", document);
      logger.log(Level.WARNING, "Commit exception", ex);
      throw new RuntimeException(ex);
    }
  }

  public SolrInputDocument createSolrDoc(String id, Map<String, List<String>> objectMap) {
    SolrInputDocument document = new SolrInputDocument();
    document.addField("id", (trimId ? id.trim() : id));
    for (Map.Entry<String, List<String>> entry : objectMap.entrySet()) {
      String fieldName = entry.getKey();
      Object value = entry.getValue();
      if (value != null) {
        if (!fieldName.endsWith("_sni") && !fieldName.endsWith("_ss")) {
          fieldName = fieldPrefix + fieldName;
          fieldName += "_ss";
        }
        document.addField(fieldName, value);

        if (indexWithTokenizedField && fieldName.endsWith("_ss"))
          document.addField(getTermFieldName(fieldName), value);
      }
    }
    return document;
  }

  private String getTermFieldName(String phraseField) {
    if (!termFieldNameCache.containsKey(phraseField))
      termFieldNameCache.put(phraseField, phraseField.replaceAll("_ss$", termFieldSuffix));
    return termFieldNameCache.get(phraseField);
  }

  public void indexDuplumKey(String id, Map<String, Object> objectMap)
      throws IOException, SolrServerException {
    SolrInputDocument document = new SolrInputDocument();
    document.addField("id", id);
    for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
      String key = entry.getKey();
      System.err.println("key: " + key);
      Object value = entry.getValue();
      if (value != null) {
        if (!key.endsWith("_sni") && !key.endsWith("_ss")) {
          key = fieldPrefix + key;
          key += "_ss";
        }
        document.addField(key, value);
      }
    }

    try {
      solrClient.add(document);
    } catch (HttpSolrClient.RemoteSolrException ex) {
      logger.log(Level.WARNING, "document", document);
      logger.log(Level.WARNING, "Commit exception", ex);
    }
  }

  public void commit() {
    try {
      solrClient.commit();
    } catch (IOException | SolrServerException e) {
      logger.log(Level.WARNING, "commit", e);
    }
  }

  public void optimize() {
    try {
      solrClient.optimize();
    } catch (IOException | SolrServerException e) {
      logger.log(Level.WARNING, "optimize", e);
    }
  }

  public SolrDocument get(String id) {
    try {
      final QueryResponse response = solrClient.query(new MapSolrParams(Map.of("q", String.format(ID_QUERY, id))));
      final SolrDocumentList documents = response.getResults();
      if (documents.getNumFound() > 0) {
        SolrDocument doc = documents.get(0);
        doc.removeFields("id");
        doc.removeFields("_version_");
        return doc;
      }
    } catch (SolrServerException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  public boolean getTrimId() {
    return trimId;
  }

  public void setTrimId(boolean trimId) {
    this.trimId = trimId;
  }

  public void indexWithTokenizedField(boolean indexWithTokenizedField) {
    this.indexWithTokenizedField = indexWithTokenizedField;
  }

  public String getFieldPrefix() {
    return fieldPrefix;
  }

  public void setFieldPrefix(String fieldPrefix) {
    this.fieldPrefix = fieldPrefix;
  }
}
