package de.gwdg.metadataqa.marc.cli;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.datastore.MarcMongodbClient;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class MongoImporter {

	private static JsonProvider jsonProvider = Configuration.defaultConfiguration().jsonProvider();
	private static final String DATABASE = "sub_last_print";
	private static final String COLLECTION = "marc";

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Please provide a host, port and file name!");
			System.exit(0);
		}

		String host = args[0];
		int port = Integer.parseInt(args[1]);
		String relativeFileName = args[2];
		Path path = Paths.get(relativeFileName);
		String fileName = path.getFileName().toString();

		MarcMongodbClient client;
		try {
			client = new MarcMongodbClient(host, port, DATABASE);
			DBCollection collection = client.getCollection(COLLECTION);

			JsonPathCache<? extends XmlFieldInstance> cache;
			List<String> records = Files.readAllLines(path, Charset.defaultCharset());
			for (String record : records) {
				cache = new JsonPathCache<>(record);
				Object jsonObject = jsonProvider.parse(record);
				String id = cache.get("$.controlfield.[?(@.tag == '001')].content").get(0).getValue();
				String x003 = cache.get("$.controlfield.[?(@.tag == '003')].content").get(0).getValue();

				BasicDBObject doc = new BasicDBObject("type", "marcjson");
				doc.append("id", id);
				doc.append("x003", x003);
				doc.append("file", fileName);
				doc.append("record", record);
				collection.insert(doc);
			}
		} catch (UnknownHostException ex) {
			Logger.getLogger(MongoImporter.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(MongoImporter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
