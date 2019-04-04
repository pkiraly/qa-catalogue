package de.gwdg.metadataqa.marc.datastore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JsonProvider;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class MarcElasticsearchClient {

  private final JsonProvider jsonProvider = Configuration.defaultConfiguration()
        .jsonProvider();
  private RestClient restClient;
  private ObjectMapper mapper;

  public MarcElasticsearchClient() {
    initialize("localhost", 9200);
  }

  public MarcElasticsearchClient(String host) {
    initialize(host, 9200);
  }

  public MarcElasticsearchClient(String host, int port) {
    initialize(host, port);
  }

  private void initialize(String host, int port) {
    mapper = new ObjectMapper();
    restClient = RestClient.builder(new HttpHost(host, port, "http")).build();
  }

  public HttpEntity rootRequest() throws IOException {
    Response response = restClient.performRequest(
          "GET", "/",
          Collections.singletonMap("pretty", "true"));
    // System.out.println(EntityUtils.toString(response.getEntity()));
    return response.getEntity();
  }

  public Response indexDuplumKey(String id, Map<String, Object> document) 
      throws IOException {
    HttpEntity entity = new ByteArrayEntity(mapper.writeValueAsBytes(document));

    Response response = restClient.performRequest(
      "PUT",
      "/sub/duplum/" + id,
      Collections.<String, String>emptyMap(),
      entity);
    return response;
  }

  public Response indexTweet(int id, String user, String message) throws IOException {
    HttpEntity entity = new NStringEntity(
      String.format("{\"user\" : \"%s\", \"message\" : \"%s\"}", user, message),
      ContentType.APPLICATION_JSON);
    Response response = restClient.performRequest(
      "PUT",
      "/twitter/tweet/" + id,
      Collections.<String, String>emptyMap(),
      entity);
    return response;
  }

  public Response deleteTweet(int id) throws IOException {
    Response response = restClient.performRequest(
          "DELETE",
          "/twitter/tweet/" + id,
          Collections.<String, String>emptyMap());
    return response;
  }

  public int getNumberOfTweets() throws IOException {
    Map<String, String> params = new HashMap<>();
    params.put("q", "*:*");
    params.put("size", "0");
    Response response = restClient.performRequest(
          "GET",
          "/twitter/tweet/_search",
          params
    );
    Object jsonObject = jsonProvider.parse(EntityUtils.toString(response.getEntity()));
    int hits = JsonPath.read(jsonObject, "$.hits.total");
    return hits;
  }

}
