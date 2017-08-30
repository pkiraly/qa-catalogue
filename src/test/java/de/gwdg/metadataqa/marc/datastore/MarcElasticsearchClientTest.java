package de.gwdg.metadataqa.marc.datastore;

import de.gwdg.metadataqa.marc.datastore.MarcElasticsearchClient;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JsonProvider;
import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import java.io.IOException;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class MarcElasticsearchClientTest {

	private Logger logger = Logger.getLogger(MarcElasticsearchClientTest.class.getCanonicalName());
	private JsonProvider jsonProvider = Configuration.defaultConfiguration().jsonProvider();

	public MarcElasticsearchClientTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void testElasticsearchRunning() throws IOException {
		MarcElasticsearchClient client = new MarcElasticsearchClient();
		HttpEntity response = client.rootRequest();
		assertNull(response.getContentEncoding());
		assertEquals("content-type", response.getContentType().getName());
		assertEquals("application/json; charset=UTF-8", response.getContentType().getValue());
		String content = EntityUtils.toString(response);
		Object jsonObject = jsonProvider.parse(content);
		// JsonPath.read(jsonObject, jsonPath);
		
		// JsonPathCache<? extends XmlFieldInstance> cache = new JsonPathCache(content);
		assertEquals("elasticsearch", JsonPath.read(jsonObject, "$.cluster_name"));
		assertEquals("hTkN47N", JsonPath.read(jsonObject, "$.name"));
		assertEquals("1gxeFwIRR5-tkEXwa2wVIw", JsonPath.read(jsonObject, "$.cluster_uuid"));
		assertEquals("You Know, for Search", JsonPath.read(jsonObject, "$.tagline"));
		assertEquals("5.5.1", JsonPath.read(jsonObject, "$.version.number"));
		assertEquals("6.6.0", JsonPath.read(jsonObject, "$.version.lucene_version"));
		assertEquals(2, client.getNumberOfTweets());
	}

	@Test
	public void testIndexTweet() throws IOException {
		MarcElasticsearchClient client = new MarcElasticsearchClient();
		Response response = client.indexTweet(2, "kimchy", "trying out Elasticsearch");
		assertEquals("HTTP/1.1 201 Created", response.getStatusLine().toString());
		assertEquals(3, response.getHeaders().length);

		assertEquals("Location", response.getHeaders()[0].getName());
		assertEquals("/twitter/tweet/2", response.getHeaders()[0].getValue());
		assertEquals("content-type", response.getHeaders()[1].getName());
		assertEquals("application/json; charset=UTF-8", response.getHeaders()[1].getValue());
		assertEquals("content-length", response.getHeaders()[2].getName());
		assertTrue(140 < Integer.parseInt(response.getHeaders()[2].getValue()));

		String json = EntityUtils.toString(response.getEntity());
		// {"_index":"twitter","_type":"tweet","_id":"2","_version":161,"result":"created","_shards":{"total":2,"successful":1,"failed":0},"created":true}
		assertTrue(json.startsWith("{\"_index\":\"twitter\",\"_type\":\"tweet\",\"_id\":\"2\",\"_version\":"));
		assertTrue(json.endsWith(",\"result\":\"created\",\"_shards\":{\"total\":2,\"successful\":1,\"failed\":0},\"created\":true}"));

		// assertEquals(2, client.getNumberOfTweets());
	}

	@Test
	public void testDeleteTweet() throws IOException {
		MarcElasticsearchClient client = new MarcElasticsearchClient();
		Response response = client.deleteTweet(2);
		assertEquals(org.apache.http.message.BasicStatusLine.class, response.getStatusLine().getClass());
		assertEquals("HTTP/1.1 200 OK", response.getStatusLine().toString());
		assertEquals(2, response.getHeaders().length);
		assertEquals("content-type", response.getHeaders()[0].getName());
		assertEquals("application/json; charset=UTF-8", response.getHeaders()[0].getValue());
		assertEquals("content-length", response.getHeaders()[1].getName());
		assertTrue(130 < Integer.parseInt(response.getHeaders()[1].getValue()));
		String json = EntityUtils.toString(response.getEntity());
		// '{"found":true,"_index":"twitter","_type":"tweet","_id":"2","_version":156,"result":"deleted","_shards":{"total":2,"successful":1,"failed":0}}'
		assertTrue(json.startsWith("{\"found\":true,\"_index\":\"twitter\",\"_type\":\"tweet\",\"_id\":\"2\",\"_version\":"));
		assertTrue(json.endsWith(",\"result\":\"deleted\",\"_shards\":{\"total\":2,\"successful\":1,\"failed\":0}}"));
		assertEquals(2, client.getNumberOfTweets());
	}
}
