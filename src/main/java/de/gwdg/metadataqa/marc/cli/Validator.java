package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.cli.parameters.ValidatorParameters;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormatter;
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
		ValidatorParameters parameters = new ValidatorParameters(args);
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

		MarcVersion marcVersion = parameters.getMarcVersion();
		if (parameters.doLog())
			logger.info("marcVersion: " + marcVersion.getCode() + ", " + marcVersion.getLabel());

		int limit = parameters.getLimit();
		if (parameters.doLog())
			logger.info("limit: " + limit);

		int offset = parameters.getOffset();
		if (parameters.doLog())
			logger.info("offset: " + offset);

		if (parameters.doLog())
			logger.info("MARC files: " + StringUtils.join(parameters.getArgs(), ", "));

		if (parameters.doLog())
			logger.info("id: " + parameters.getId());

		File output = null;
		if (!parameters.useStandardOutput()) {
			output = new File(parameters.getFileName());
			if (output.exists())
				output.delete();
		}

		String[] inputFileNames = parameters.getArgs();

		int i = 0;
		String lastKnownId = "";
		for (String inputFileName : inputFileNames) {
			Path path = Paths.get(inputFileName);
			String fileName = path.getFileName().toString();

			if (parameters.doLog())
				logger.info("processing: " + fileName);

			try {
				MarcReader reader = ReadMarc.getReader(path.toString());
				while (reader.hasNext()) {
					Record marc4jRecord = reader.next();
					i++;
					if (isUnderOffset(offset, i)) {
						continue;
					}
					if (isOverLimit(limit, i)) {
						break;
					}


					if (marc4jRecord.getControlNumber() == null) {
						logger.severe("No record number at " + i + ", last known ID: " + lastKnownId);
						System.err.println(marc4jRecord);
						continue;
					} else {
						lastKnownId = marc4jRecord.getControlNumber();
					}

					if (parameters.hasId() && !marc4jRecord.getControlNumber().equals(parameters.getId()))
						continue;

					try {
						MarcRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord);
						if (marcRecord.getId() == null)
							logger.severe("No record number at " + i);

						boolean isValid = marcRecord.validate(marcVersion, parameters.doSummary());
						if (!isValid) {
							if (marcRecord.getErrors().size() != marcRecord.getValidationErrors().size()) {
								logger.severe(String.format("differents!! string: %d vs obj: %d",
									marcRecord.getErrors().size(),
									marcRecord.getValidationErrors().size()));
								logger.severe("strings\n" + StringUtils.join(marcRecord.getErrors(), "\n"));
								logger.severe("objects\n" + StringUtils.join(marcRecord.getValidationErrors(), "\n"));
							}
							if (parameters.doSummary()) {
								for (String error : ValidationErrorFormatter.formatForSummary(marcRecord.getValidationErrors(), parameters.getFormat())) {
									if (!errorCounter.containsKey(error)) {
										errorCounter.put(error, 0);
									}
									errorCounter.put(error, errorCounter.get(error) + 1);
								}
							} else {
								String message = ValidationErrorFormatter.format(marcRecord.getValidationErrors(), parameters.getFormat());
								if (parameters.useStandardOutput())
									System.out.print(message);
								else
									FileUtils.writeStringToFile(output, message, true);
							}
						}

						if (i % 100000 == 0 && parameters.doLog())
							logger.info(String.format("%s/%d (%s)", fileName, i, marcRecord.getId()));
					} catch (IllegalArgumentException e) {
						if (marc4jRecord.getControlNumber() == null)
							logger.severe("No record number at " + i);
						if (parameters.doLog())
							logger.severe(String.format("Error with record '%s'. %s", marc4jRecord.getControlNumber(), e.getMessage()));
						continue;
					}
				}
				if (parameters.doLog())
					logger.info(String.format("Finished processing file. Validated %d records.", i));

			} catch(SolrServerException ex){
				if (parameters.doLog())
					logger.severe(ex.toString());
				System.exit(0);
			} catch(Exception ex){
				if (parameters.doLog())
					logger.severe(ex.toString());
				ex.printStackTrace();
				System.exit(0);
			}
		}

		if (parameters.doSummary()) {
			try {
				String message;
				for (String error : errorCounter.keySet()) {
					message = String.format("%s (%d times)\n", error, errorCounter.get(error));
					if (parameters.useStandardOutput())
						System.out.print(message);
					else
						FileUtils.writeStringToFile(output, message, true);
				}
			} catch (IOException ex) {
				if (parameters.doLog())
					logger.severe(ex.toString());
				ex.printStackTrace();
				System.exit(0);
			}
		}

		long end = System.currentTimeMillis();
		long duration = (end - start) / 1000;
		if (parameters.doLog())
			logger.info(String.format("Bye! It took: %s",
				LocalTime.MIN.plusSeconds(duration).toString()));

		System.exit(0);
	}

	private static boolean isOverLimit(int limit, int i) {
		return limit > -1 && i > limit;
	}

	private static boolean isUnderOffset(int offset, int i) {
		return offset > -1 && i < offset;
	}

	private static void printHelp(Options opions) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -cp metadata-qa-marc.jar de.gwdg.metadataqa.marc.cli.Validator [options] [file]",
			opions);
	}
}