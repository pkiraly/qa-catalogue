package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.cli.parameters.FormatterParameters;
import de.gwdg.metadataqa.marc.cli.parameters.ValidatorParameters;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * usage:
 * java -cp target/metadata-qa-marc-0.1-SNAPSHOT-jar-with-dependencies.jar de.gwdg.metadataqa.marc.cli.Validator [MARC21 file]
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Formatter {

	private static final Logger logger = Logger.getLogger(Formatter.class.getCanonicalName());

	public static void main(String[] args) throws ParseException {
		FormatterParameters parameters = new FormatterParameters(args);

		if (parameters.getArgs().length < 1) {
			System.err.println("Please provide a MARC file name!");
			System.exit(0);
		}
		if (parameters.doHelp()) {
			printHelp(parameters.getOptions());
			System.exit(0);
		}

		long start = System.currentTimeMillis();
		Map<String, Integer> errorCounter = new TreeMap<>();

		String format = parameters.getFormat();
		System.err.println("format: " + format);

		String id = parameters.getId();
		System.err.println("id: " + id);

		String[] inputFileNames = parameters.getArgs();

		if (parameters.hasSearch()) {
			System.err.printf("path: %s, query: %s\n", parameters.getPath(), parameters.getQuery());
		}

		int i = 0;
		for (String inputFileName : inputFileNames) {
			Path path = Paths.get(inputFileName);
			String fileName = path.getFileName().toString();

			if (parameters.doLog())
				logger.info("processing: " + fileName);

			try {
				MarcReader reader = ReadMarc.getReader(path.toString());

				while (reader.hasNext()) {
					i++;

					Record marc4jRecord = reader.next();
					try {
						// System.err.println(marc4jRecord.getControlNumber());
						if ((marc4jRecord.getControlNumber() != null && marc4jRecord.getControlNumber().equals(id))
							|| (parameters.getCountNr() > -1 && parameters.getCountNr() == i)) {
							System.out.println(marc4jRecord.toString());
							break;
						}
						if (parameters.hasSearch()) {
							MarcRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord);
							List<String> results = marcRecord.search(parameters.getPath(), parameters.getQuery());
							if (!results.isEmpty()) {
								System.out.println(marc4jRecord.toString());
								break;
							}
						}
					} catch (IllegalArgumentException e) {
						logger.severe(String.format("Error with record '%s'. %s", marc4jRecord.getControlNumber(), e.getMessage()));
						continue;
					}
				}

				logger.info(String.format("End of cycle. Validated %d records.", i));
			} catch(SolrServerException ex){
				logger.severe(ex.toString());
				System.exit(0);
			} catch(Exception ex){
				logger.severe(ex.toString());
				ex.printStackTrace();
				System.exit(0);
			}
		}

		long end = System.currentTimeMillis();
		long duration = (end - start) / 1000;
		logger.info(String.format("Bye! It took: %s", LocalTime.MIN.plusSeconds(duration).toString()));

		System.exit(0);
	}

	private static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -cp metadata-qa-marc.jar de.gwdg.metadataqa.marc.cli.Validator [options] [file]", options);
	}
}