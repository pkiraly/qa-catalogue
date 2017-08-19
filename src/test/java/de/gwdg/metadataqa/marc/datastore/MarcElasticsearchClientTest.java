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
	public void hello() throws IOException {
		MarcElasticsearchClient client = new MarcElasticsearchClient();
		HttpEntity response = client.rootRequest();
		System.err.println("ContentEncoding: " + response.getContentEncoding());
		System.err.println("ContentType: " + response.getContentType());
		String content = EntityUtils.toString(response);
		System.err.println("content: " + content);
		Object jsonObject = jsonProvider.parse(content);
		// JsonPath.read(jsonObject, jsonPath);
		
		// JsonPathCache<? extends XmlFieldInstance> cache = new JsonPathCache(content);
		assertEquals("5.5.1", JsonPath.read(jsonObject, "$.version.number"));
		assertEquals("6.6.0", JsonPath.read(jsonObject, "$.version.lucene_version"));
		System.out.println("NumberOfTweets: " + client.getNumberOfTweets());
	}

	@Test
	public void testIndexTweet() throws IOException {
		MarcElasticsearchClient client = new MarcElasticsearchClient();
		Response response = client.indexTweet(2, "kimchy", "trying out Elasticsearch");
		System.err.println("[testIndexTweet]");
		System.err.println("status line: " + response.getStatusLine());
		System.err.println("headers:[\n" + StringUtils.join(response.getHeaders(), "\n") + "]\n");
		System.out.println(EntityUtils.toString(response.getEntity()));
		System.out.println("NumberOfTweets: " + client.getNumberOfTweets());
	}

	@Test
	public void testDeleteTweet() throws IOException {
		MarcElasticsearchClient client = new MarcElasticsearchClient();
		Response response = client.deleteTweet(2);
		System.err.println("[testDeleteTweet]");
		System.err.println("status line: " + response.getStatusLine());
		System.err.println("headers:[\n" + StringUtils.join(response.getHeaders(), "\n") + "]\n");
		System.out.println(EntityUtils.toString(response.getEntity()));
		logger.info("NumberOfTweets: " + client.getNumberOfTweets());
	}
}
