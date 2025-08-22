package de.gwdg.metadataqa.marc.datastore;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BaseHttpSolrClient;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.util.NamedList;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarcSolrClient {

  private static final Logger logger = Logger.getLogger(MarcSolrClient.class.getCanonicalName());
  public static final String ID_QUERY = "id:\"%s\"";

  private final String defaultUrl = "http://localhost:8983/solr";
  private SolrClient solrClient;
  private boolean trimId = false;
  private boolean indexWithTokenizedField = false;
  private final String termFieldSuffix = "_tt";
  private String fieldPrefix = "";
  private final Map<String, String> termFieldNameCache = new HashMap<>();

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
    solrClient = new Http2SolrClient.Builder(url).build();
  }

  public void indexMap(String id, Map<String, List<String>> objectMap) {
    index(createSolrDoc(id, objectMap));
  }

  public void index(SolrInputDocument document) {
    try {
      solrClient.add(document);
      // logger.log(Level.INFO, document.getField("id").toString() + " indexed");
    } catch (BaseHttpSolrClient.RemoteSolrException | SolrServerException | IOException ex) {
      logger.log(Level.WARNING, "document", document);
      logger.log(Level.WARNING, "Commit exception", ex);
      throw new RuntimeException(ex);
    } catch (Exception e) {
      logger.log(Level.WARNING, "Other kind of commit exception", e);
      throw new RuntimeException(e);
    }
  }

  public SolrInputDocument createSolrDoc(String id, Map<String, List<String>> objectMap) {
    SolrInputDocument document = new SolrInputDocument();
    document.addField("id", (trimId ? id.trim() : id));
    for (Map.Entry<String, List<String>> entry : objectMap.entrySet()) {
      String fieldName = entry.getKey();
      Object value = entry.getValue();
      if (value == null) {
        continue;
      }
      if (!fieldName.endsWith("_sni") && !fieldName.endsWith("_ss")) {
        fieldName = fieldPrefix + fieldName;
        fieldName += "_ss";
      }
      document.addField(fieldName, value);

      if (indexWithTokenizedField && fieldName.endsWith("_ss"))
        document.addField(getTermFieldName(fieldName), value);
    }
    return document;
  }

  private String getTermFieldName(String phraseField) {
    termFieldNameCache.putIfAbsent(phraseField, phraseField.replaceAll("_ss$", termFieldSuffix));
    return termFieldNameCache.get(phraseField);
  }

  public void indexDuplumKey(String id, Map<String, Object> objectMap)
      throws IOException, SolrServerException {
    SolrInputDocument document = new SolrInputDocument();
    document.addField("id", id);
    for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      if (value == null) {
        continue;
      }
      if (!key.endsWith("_sni") && !key.endsWith("_ss")) {
        key = fieldPrefix + key;
        key += "_ss";
      }
      document.addField(key, value);
    }

    try {
      solrClient.add(document);
    } catch (BaseHttpSolrClient.RemoteSolrException ex) {
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

  /**
   * Given an id, return the corresponding SolrDocument from the index, and also remove the id field.
   */
  public SolrDocument get(String id) {
    try {
      final QueryResponse response = solrClient.query(new MapSolrParams(Map.of("q", String.format(ID_QUERY, id))));
      final SolrDocumentList documents = response.getResults();
      if (documents.getNumFound() <= 0) {
        return null;
      }
      SolrDocument doc = documents.get(0);
      doc.removeFields("id");
      doc.removeFields("_version_");
      return doc;
    } catch (SolrServerException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public long getCount() {
    try {
      final QueryResponse response = solrClient.query(new MapSolrParams(Map.of(
        "q", "*:*",
        "rows", "0")));
      return response.getResults().getNumFound();
    } catch (SolrServerException | IOException e) {
      logger.severe(e.getMessage());
    }
    return 0;
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
