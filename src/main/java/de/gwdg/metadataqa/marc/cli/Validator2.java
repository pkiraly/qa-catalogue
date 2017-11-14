package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.cli.parameters.ValidatorParameters;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormatter;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * usage:
 * java -cp target/metadata-qa-marc-0.1-SNAPSHOT-jar-with-dependencies.jar de.gwdg.metadataqa.marc.cli.Validator [MARC21 file]
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Validator2 implements MarcFileProcessor {

	private static final Logger logger = Logger.getLogger(Validator2.class.getCanonicalName());
	private static Options options;

	private ValidatorParameters parameters;
	private Map<String, Integer> errorCounter = new TreeMap<>();
	private File output = null;

	public Validator2(String[] args) throws ParseException {
		parameters = new ValidatorParameters(args);
		errorCounter = new TreeMap<>();
	}

	@Override
	public ValidatorParameters getParameters() {
		return parameters;
	}

	@Override
	public void beforeIteration() {
		logger.info(parameters.getFileName());
		if (!parameters.useStandardOutput()) {
			output = new File(parameters.getFileName());
			if (output.exists())
				output.delete();
			logger.info("output: " + output.getPath());
		}
	}

	@Override
	public void fileOpened() {

	}

	@Override
	public void fileProcessed() {

	}

	@Override
	public void afterIteration() {
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
	}

	@Override
	public void processRecord(MarcRecord marcRecord, int i) throws IOException {
		if (marcRecord.getId() == null)
			logger.severe("No record number at " + i);

		boolean isValid = marcRecord.validate(parameters.getMarcVersion(), parameters.doSummary());
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
	}

	private static void printHelp(Options opions) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -cp metadata-qa-marc.jar de.gwdg.metadataqa.marc.cli.Validator [options] [file]",
			opions);
	}

	public static void main(String[] args) throws ParseException {
		MarcFileProcessor processor = new Validator2(args);
		if (processor.getParameters().getArgs().length < 1) {
			System.err.println("Please provide a MARC file name!");
			System.exit(0);
		}
		if (processor.getParameters().doHelp()) {
			printHelp(processor.getParameters().getOptions());
			System.exit(0);
		}
		RecordIterator iterator = new RecordIterator(processor);
		iterator.start();
	}
}