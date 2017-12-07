package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.cli.parameters.MarcToSolrParameters;
import de.gwdg.metadataqa.marc.datastore.MarcSolrClient;
import de.gwdg.metadataqa.marc.utils.ReadMarc;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.logging.Logger;

/**
 * usage:
 * java -cp target/metadata-qa-marc-0.1-SNAPSHOT-jar-with-dependencies.jar de.gwdg.metadataqa.marc.cli.SolrKeyGenerator http://localhost:8983/solr/tardit 0001.0000000.formatted.json
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class MarcToSolr {

	private static final Logger logger = Logger.getLogger(MarcToSolr.class.getCanonicalName());

	public static void main(String[] args) throws ParseException {
		MarcToSolrParameters parameters = new MarcToSolrParameters(args);

		if (StringUtils.isBlank(parameters.getSolrUrl())) {
			System.err.println("Please provide a Solr URL and file name!");
			System.exit(0);
		}
		long start = System.currentTimeMillis();

		// logger.info(String.format("Solr URL: %s, file: %s (do commits: %s)", url, fileName, doCommits));

		MarcSolrClient client = new MarcSolrClient(parameters.getSolrUrl());

		String[] inputFileNames = parameters.getArgs();

		int i = 0;
		for (String inputFileName : inputFileNames) {
			Path path = Paths.get(inputFileName);
			String fileName = path.getFileName().toString();

			if (parameters.doLog())
				logger.info("processing: " + fileName);

			try {
				MarcReader reader = ReadMarc.getStreamReader(path.toString());
				while (reader.hasNext()) {
					i++;
					Record marc4jRecord = reader.next();
					try {
						MarcRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord);
						client.indexMap(
							marcRecord.getId(),
							marcRecord.getKeyValuePairs(parameters.getSolrFieldType()));
						if (i % 1000 == 0) {
							if (parameters.doCommit())
								client.commit();
							logger.info(String.format("%s/%d (%s)", fileName, i, marcRecord.getId()));
						}
					} catch (IllegalArgumentException e) {
						logger.severe(e.getMessage());
					}
				}
				if (parameters.doCommit())
					client.commit();
				logger.info(String.format("End of cycle. Indexed %d records.", i));
			} catch (SolrServerException ex) {
				logger.severe(ex.toString());
				System.exit(0);
			} catch (Exception ex) {
				logger.severe(ex.toString());
				ex.printStackTrace();
				System.exit(0);
			}
		}
		long end = System.currentTimeMillis();

		long duration = (end - start) / 1000;
		logger.info(String.format("Bye! It took: %s",
			LocalTime.MIN.plusSeconds(duration).toString()));

		System.exit(0);
	}
}