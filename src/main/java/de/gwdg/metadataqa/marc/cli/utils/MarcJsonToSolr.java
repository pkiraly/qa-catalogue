package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.api.model.selector.JsonSelector;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.datastore.MarcSolrClient;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
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

    logger.log(Level.INFO, "Solr URL: {0}, file: {1} (do commits: {2})", new Object[]{url, fileName, doCommits});

    MarcSolrClient client = new MarcSolrClient(url);
    JsonSelector<? extends XmlFieldInstance> cache;
    List<String> records;
    try {
      records = Files.readAllLines(path, Charset.defaultCharset());
      var i = 0;
      for (String marcRecordLine : records) {
        i++;
        cache = new JsonSelector(marcRecordLine);
        BibliographicRecord marcRecord = MarcFactory.create(cache);
        client.indexMap(marcRecord.getId(), marcRecord.getKeyValuePairs());

        if (i % 1000 == 0) {
          if (doCommits)
            client.commit();
          logger.log(Level.INFO, "{0}/{1}) {2}", new Object[]{fileName, i, marcRecord.getId()});
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

    logger.log(Level.INFO, "Bye! It took: {0} s", new Object[]{String.format("%.1f", (float) (end - start) / 1000)});

    System.exit(0);
  }
}
