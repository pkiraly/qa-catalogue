package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.api.model.pathcache.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.datastore.MarcSolrClient;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

/**
 * usage:
 * java -cp target/metadata-qa-marc-0.1-SNAPSHOT-jar-with-dependencies.jar de.gwdg.metadataqa.marc.cli.SolrKeyGenerator http://localhost:8983/solr/tardit 0001.0000000.formatted.json
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class MarcJsonToSolr {

  private static final Logger logger = Logger.getLogger(MarcJsonToSolr.class.getCanonicalName());

  public static void main(String[] args) {
    if (args.length < 2) {
      System.err.println("Please provide a Solr URL and file name!");
      System.exit(0);
    }
    long start = System.currentTimeMillis();

    String url = args[0];

    String relativeFileName = args[1];
    var path = Paths.get(relativeFileName);
    String fileName = path.getFileName().toString();

    boolean doCommits = true;
    if (args.length > 2) {
      if (args[2].equals("doCommit=true"))
        doCommits = true;
      else if (args[2].equals("doCommit=false"))
        doCommits = false;
    }

    logger.info(String.format("Solr URL: %s, file: %s (do commits: %s)", url, fileName, doCommits));

    MarcSolrClient client = new MarcSolrClient(url);
    JsonPathCache<? extends XmlFieldInstance> cache;
    List<String> records;
    try {
      records = Files.readAllLines(path, Charset.defaultCharset());
      var i = 0;
      for (String marcRecordLine : records) {
        i++;
        cache = new JsonPathCache(marcRecordLine);
        MarcRecord marcRecord = MarcFactory.create(cache);
        client.indexMap(marcRecord.getId(), marcRecord.getKeyValuePairs());

        if (i % 1000 == 0) {
          if (doCommits)
            client.commit();
          logger.info(String.format("%s/%d) %s", fileName, i, marcRecord.getId()));
        }
      }
      if (doCommits)
        client.commit();
      logger.info("end of cycle");
    } catch (IOException | SolrServerException ex) {
      logger.severe(ex.toString());
      System.exit(0);
    }
    long end = System.currentTimeMillis();

    logger.info(String.format("Bye! It took: %.1f s", (float) (end - start) / 1000));

    System.exit(0);
  }
}
