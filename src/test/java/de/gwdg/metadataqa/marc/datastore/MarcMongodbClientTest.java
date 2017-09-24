package de.gwdg.metadataqa.marc.datastore;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.util.FileUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class MarcMongodbClientTest {

	private JsonProvider jsonProvider = Configuration.defaultConfiguration().jsonProvider();

	public MarcMongodbClientTest() {
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

	// @Test
	public void testConstructor() throws UnknownHostException {
		MarcMongodbClient client = new MarcMongodbClient("localhost" , 27017, "sub_last_print");
	}

	// @Test
	public void testGetCollection() throws UnknownHostException {
		MarcMongodbClient client = new MarcMongodbClient("localhost" , 27017, "sub_last_print");
		DBCollection collection = client.getCollection("marc");
		assertNotNull(collection);
		assertEquals(0, collection.count());
	}

	// @Test
	public void testInsert() throws UnknownHostException {
		MarcMongodbClient client = new MarcMongodbClient("localhost" , 27017, "sub_last_print");
		DBCollection collection = client.getCollection("marc");
		BasicDBObject doc = createTestObject();
		collection.insert(doc);
		assertNotNull(collection);
		assertEquals(1, collection.count());
		collection.remove(new BasicDBObject("name", "MongoDB"));
		assertEquals(0, collection.count());
	}

	// @Test
	public void testFindOne() throws UnknownHostException {
		MarcMongodbClient client = new MarcMongodbClient("localhost" , 27017, "sub_last_print");
		DBCollection collection = client.getCollection("marc");
		BasicDBObject doc = createTestObject();
		collection.insert(doc);
		assertEquals(1, collection.count());
		DBObject myDoc = collection.findOne();
		assertEquals("MongoDB", myDoc.get("name"));
		assertEquals("database", myDoc.get("type"));
		assertEquals(1, myDoc.get("count"));
		assertEquals(BasicDBObject.class, myDoc.get("info").getClass());
		assertEquals(new BasicDBObject("x", 203).append("y", 102), myDoc.get("info"));
		assertEquals(203, ((BasicDBObject)myDoc.get("info")).get("x"));
		assertEquals(Integer.class, ((BasicDBObject)myDoc.get("info")).get("x").getClass());
		System.out.println(myDoc);
		collection.remove(new BasicDBObject("name", "MongoDB"));
	}

	// @Test
	public synchronized void testImport() throws URISyntaxException, IOException, InterruptedException {
		MarcMongodbClient client = new MarcMongodbClient("localhost" , 27017, "sub_last_print");
		DBCollection collection = client.getCollection("marc");
		assertEquals(0, collection.count());
		boolean insert = true;
		if (insert) {
			JsonPathCache<? extends XmlFieldInstance> cache;
			List<String> records = FileUtils.readLines("general/marc.json");
			for (String record : records) {
				cache = new JsonPathCache<>(record);
				Object jsonObject = jsonProvider.parse(record);
				String id   = cache.get("$.controlfield.[?(@.tag == '001')].content").get(0).getValue();
				String x003 = cache.get("$.controlfield.[?(@.tag == '003')].content").get(0).getValue();

				BasicDBObject doc = new BasicDBObject("type", "marcjson")
					.append("id", id)
					.append("x003", x003)
					.append("record", record);
				collection.insert(doc);
			}
			assertEquals(674, collection.count());
		}
		collection.remove(new BasicDBObject("type", "marcjson"));
		assertEquals(0, collection.count());
	}


	private BasicDBObject createTestObject() {
		BasicDBObject doc = new BasicDBObject("name", "MongoDB")
				  .append("type", "database")
				  .append("count", 1)
				  .append("info", new BasicDBObject("x", 203).append("y", 102));
		return doc;
	}

}
