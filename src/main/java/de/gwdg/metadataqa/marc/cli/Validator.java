package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
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
import java.io.IOException;
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
public class Validator {

	private static final Logger logger = Logger.getLogger(Validator.class.getCanonicalName());
	private static Options options;

	public static void main(String[] args) throws ParseException {
		CommandLine cmd = processCommandLine(args);
		ValidatorParameters parameters = new ValidatorParameters(cmd);
		if (cmd.getArgs().length < 1) {
			System.err.println("Please provide a MARC file name!");
			System.exit(0);
		}
		if (cmd.hasOption("help")) {
			printHelp();
			System.exit(0);
		}

		long start = System.currentTimeMillis();
		Map<String, Integer> errorCounter = new TreeMap<>();

		MarcVersion marcVersion = parameters.getMarcVersion();
		logger.info("marcVersion: " + marcVersion.getCode() + ", " + marcVersion.getLabel());

		int limit = parameters.getLimit();
		logger.info("limit: " + limit);

		int offset = parameters.getOffset();
		logger.info("offset: " + offset);

		logger.info("MARC files: " + StringUtils.join(cmd.getArgs(), ", "));

		File output = new File(parameters.getFileName());
		if (output.exists())
			output.delete();

		String[] inputFileNames = cmd.getArgs();

		int i = 0;
		for (String inputFileName : inputFileNames) {
			Path path = Paths.get(inputFileName);
			String fileName = path.getFileName().toString();
			logger.info("processing: " + fileName);

			try {
				MarcReader reader = ReadMarc.getReader(path.toString());
				while (reader.hasNext()) {
					i++;
					if (isUnderOffset(offset, i)) {
						continue;
					}
					if (isOverLimit(limit, i)) {
						break;
					}

					Record marc4jRecord = reader.next();
					try {
						MarcRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord);
						boolean isValid = marcRecord.validate(marcVersion);
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

						if (i % 10000 == 0)
							logger.info(String.format("%s/%d) %s", fileName, i, marcRecord.getId()));
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

		if (cmd.hasOption("summary")) {
			try {
				for (String error : errorCounter.keySet()) {
					FileUtils.writeStringToFile(output, String.format("%s (%d times)\n", error, errorCounter.get(error)), true);
				}
			} catch (IOException ex) {
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

	private static boolean isOverLimit(int limit, int i) {
		return limit > -1 && i > limit;
	}

	private static boolean isUnderOffset(int offset, int i) {
		return offset > -1 && offset < i;
	}

	private static CommandLine processCommandLine(String[] args) throws ParseException {
		options = new Options();
		options.addOption("s", "summary", false, "show summary instead of record level display");
		options.addOption("m", "marcVersion", true, "MARC version ('OCLC' or DNB')");
		options.addOption("l", "limit", true, "limit the number of records to process");
		options.addOption("o", "offset", true, "the first record to process");
		options.addOption("f", "fileName", true,
			String.format("the report file name (default is %s)", ValidatorParameters.DEFAULT_FILE_NAME));
		options.addOption("h", "help", false, "display help");

		CommandLineParser parser = new DefaultParser();
		return parser.parse(options, args);
	}

	private static void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -cp metadata-qa-marc.jar de.gwdg.metadataqa.marc.cli.Validator [options] [file]", options);
	}
}