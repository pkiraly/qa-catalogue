package de.gwdg.metadataqa.marc.datastore;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient.Builder;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.elasticsearch.client.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MarcSolrClient {
    private String defaultUrl = "http://localhost:8983/solr/techproducts";
    private SolrClient solr;

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
        document.addField("id", id);
        for (String key : objectMap.keySet()) {
            Object value = objectMap.get(key);
            if (value != null) {
                // System.err.printf("%s: class: %s\n", key, value.getClass());
                document.addField(key + "_ss", value);
            }
        }

        try {
            UpdateResponse response = solr.add(document);
            // solr.commit();
        } catch (HttpSolrClient.RemoteSolrException ex) {
            System.err.printf("document: %s", document);
            System.err.printf("Commit exception: %s\n", ex.getMessage());
        }
    }

    public void indexDuplumKey(String id, Map<String, Object> objectMap)
            throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", id);
        for (String key : objectMap.keySet()) {
            Object value = objectMap.get(key);
            if (value != null) {
                // System.err.printf("%s: class: %s\n", key, value.getClass());
                document.addField(key + "_ss", value);
            }
        }

        try {
            UpdateResponse response = solr.add(document);
            // solr.commit();
        } catch (HttpSolrClient.RemoteSolrException ex) {
            System.err.printf("document: %s", document);
            System.err.printf("Commit exception: %s\n", ex.getMessage());
        }
    }

    public void commit() {
        try {
            solr.commit();
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }
    }

    public void optimize() {
        try {
            solr.optimize();
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }
    }
}
