package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.cli.parameters.ValidatorParameters;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormatter;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.marc4j.marc.Record;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * usage:
 * java -cp target/metadata-qa-marc-0.1-SNAPSHOT-jar-with-dependencies.jar de.gwdg.metadataqa.marc.cli.Validator [MARC21 file]
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Validator implements MarcFileProcessor, Serializable {

	private static final Logger logger = Logger.getLogger(Validator.class.getCanonicalName());
	private static Options options;

	private ValidatorParameters parameters;
	private Map<String, Integer> errorCounter = new TreeMap<>();
	private File output = null;
	private boolean doPrintInProcessRecord = true;

	public Validator(String[] args) throws ParseException {
		parameters = new ValidatorParameters(args);
		errorCounter = new TreeMap<>();
	}

	public static void main(String[] args) throws ParseException {
		MarcFileProcessor processor = new Validator(args);
		if (processor.getParameters().getArgs().length < 1) {
			System.err.println("Please provide a MARC file name!");
			System.exit(0);
		}
		if (processor.getParameters().doHelp()) {
			processor.printHelp(processor.getParameters().getOptions());
			System.exit(0);
		}
		RecordIterator iterator = new RecordIterator(processor);
		iterator.start();
	}

	public void printHelp(Options opions) {
		HelpFormatter formatter = new HelpFormatter();
		String message = String.format("java -cp metadata-qa-marc.jar %s [options] [file]", this.getClass().getCanonicalName());
		formatter.printHelp(message, options);
	}

	@Override
	public ValidatorParameters getParameters() {
		return parameters;
	}

	@Override
	public void beforeIteration() {
		logger.info(parameters.formatParameters());
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
				for (Map.Entry<String, Integer> entry : errorCounter.entrySet()) {
					String error = entry.getKey();
					message = String.format("%s (%d times)%n", error, entry.getValue());
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
	public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {

	}

	@Override
	public void processRecord(MarcRecord marcRecord, int i) throws IOException {
		if (marcRecord.getId() == null)
			logger.severe("No record number at " + i);

		boolean isValid = marcRecord.validate(parameters.getMarcVersion(), parameters.doSummary());
		if (!isValid && doPrintInProcessRecord) {
			if (parameters.doSummary()) {
				List<String> errors = ValidationErrorFormatter.formatForSummary(
					marcRecord.getValidationErrors(), parameters.getFormat()
				);
				for (String error : errors) {
					if (!errorCounter.containsKey(error)) {
						errorCounter.put(error, 0);
					}
					errorCounter.put(error, errorCounter.get(error) + 1);
				}
			} else {
				String message = ValidationErrorFormatter.format(marcRecord.getValidationErrors(), parameters.getFormat());
				if (parameters.useStandardOutput())
					System.out.print(message);
				else {
					FileUtils.writeStringToFile(output, message, true);
				}
			}
		}
	}

	public boolean doPrintInProcessRecord() {
		return doPrintInProcessRecord;
	}

	public void setDoPrintInProcessRecord(boolean doPrintInProcessRecord) {
		this.doPrintInProcessRecord = doPrintInProcessRecord;
	}
}