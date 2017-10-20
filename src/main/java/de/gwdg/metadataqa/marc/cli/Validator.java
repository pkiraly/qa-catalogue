package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.datastore.MarcSolrClient;
import de.gwdg.metadataqa.marc.utils.ReadMarc;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * usage:
 * java -cp target/metadata-qa-marc-0.1-SNAPSHOT-jar-with-dependencies.jar de.gwdg.metadataqa.marc.cli.SolrKeyGenerator http://localhost:8983/solr/tardit 0001.0000000.formatted.json
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Validator {

	private static final Logger logger = Logger.getLogger(Validator.class.getCanonicalName());
	private static Options options;

	public static void main(String[] args) throws ParseException {
		CommandLine cmd = processCommandLine(args);
		if (cmd.getArgs().length < 1) {
			System.err.println("Please provide a MARC file name!");
			System.exit(0);
		}
		if (cmd.hasOption("help")) {
			printHelp();
			System.exit(0);
		}

		long start = System.currentTimeMillis();
		Map<String, Integer> errorCounter = new LinkedHashMap<>();

		String relativeFileName = cmd.getArgs()[0];
		System.err.println("relativeFileName: " + relativeFileName);
		Path path = Paths.get(relativeFileName);
		String fileName = path.getFileName().toString();

		JsonPathCache<? extends XmlFieldInstance> cache;
		List<String> records;
		try {
			MarcReader reader = ReadMarc.getReader(path.toString());

			File output = new File("validation-report.txt");
			if (output.exists())
				output.delete();

			int i = 0;
			while (reader.hasNext()) {
				i++;
				Record marc4jRecord = reader.next();
				try {
					MarcRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord);
					boolean isValid = marcRecord.validate();
					if (!isValid) {
						if (cmd.hasOption("summary")) {
							for (String error : marcRecord.getErrors()) {
								if (!errorCounter.containsKey(error)) {
									errorCounter.put(error, 0);
								}
								errorCounter.put(error, errorCounter.get(error) + 1);
							}
						} else {
							String message = String.format(
								"%s in '%s': \n\t%s\n",
								(marcRecord.getErrors().size() == 1 ? "Error" : "Errors"),
								marcRecord.getControl001().getContent(),
								StringUtils.join(marcRecord.getErrors(), "\n\t")
							);
							FileUtils.writeStringToFile(output, message, true);
						}
					}

					if (i % 1000 == 0)
						logger.info(String.format("%s/%d) %s", fileName, i, marcRecord.getId()));
				} catch (IllegalArgumentException e) {
					logger.severe(String.format("Error with record '%s'. %s", marc4jRecord.getControlNumber(), e.getMessage()));
					continue;
				}
			}

			logger.info(String.format("End of cycle. Validated %d records.", i));

			if (cmd.hasOption("summary")) {
				for (String error : errorCounter.keySet()) {
					FileUtils.writeStringToFile(output, String.format("%s (%d)\n", error, errorCounter.get(error)), true);
				}
			}
		} catch(SolrServerException ex){
			logger.severe(ex.toString());
			System.exit(0);
		} catch(Exception ex){
			logger.severe(ex.toString());
			ex.printStackTrace();
			System.exit(0);
		}

		long end = System.currentTimeMillis();
		long duration = (end - start) / 1000;
		logger.info(String.format("Bye! It took: %s", LocalTime.MIN.plusSeconds(duration).toString()));

		System.exit(0);
	}

	private static CommandLine processCommandLine(String[] args) throws ParseException {
		options = new Options();
		options.addOption("s", "summary", false, "show summary instead of record level display");
		options.addOption("h", "help", false, "display help");

		CommandLineParser parser = new DefaultParser();
		return parser.parse(options, args);
	}

	private static void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -cp metadata-qa-marc.jar de.gwdg.metadataqa.marc.cli.Validator [options] [file]", options);
	}
}