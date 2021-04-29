package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.api.model.pathcache.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.schema.MarcJsonSchema;
import de.gwdg.metadataqa.marc.MarcFieldExtractor;
import de.gwdg.metadataqa.marc.datastore.MarcElasticsearchClient;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class ElasticsearchKeyGenerator {

  private static final Logger logger = Logger.getLogger(ElasticsearchKeyGenerator.class.getCanonicalName());

  public static void main(String[] args) {
    if (args.length != 3) {
      System.err.println("Please provide a host, port and file name!");
      System.exit(0);
    }

    String host = args[0];
    var port = Integer.parseInt(args[1]);
    String relativeFileName = args[2];
    var path = Paths.get(relativeFileName);
    var fileName = path.getFileName().toString();

    logger.info(String.format("host: %s, port: %d, file: %s", host, port, path.getFileName().toString()));

    var client = new MarcElasticsearchClient(host, port);
    JsonPathCache<? extends XmlFieldInstance> cache;
    List<String> records;
    try {
      records = Files.readAllLines(path, Charset.defaultCharset());
      var extractor = new MarcFieldExtractor(new MarcJsonSchema());
      Map<String, Object> duplumKey;
      var i = 0;
      for (String marcRecord : records) {
        i++;
        cache = new JsonPathCache(marcRecord);
        extractor.measure(cache);
        duplumKey = extractor.getDuplumKeyMap();
        client.indexDuplumKey((String)duplumKey.get("recordId"), duplumKey);
        if (i % 100 == 0) {
          logger.info(String.format("%s/%d) %s", fileName, i, duplumKey.get("recordId")));
        }
      }
      logger.info("end of cycle");
    } catch (IOException ex) {
      logger.severe(ex.toString());
      System.exit(0);
    }
    logger.info("Bye!");
    System.exit(0);
  }
}
