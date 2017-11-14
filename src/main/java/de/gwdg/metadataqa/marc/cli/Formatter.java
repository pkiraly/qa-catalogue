package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.FormatterParameters;
import de.gwdg.metadataqa.marc.cli.parameters.ValidatorParameters;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
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
public class Formatter implements MarcFileProcessor {

	private static final Logger logger = Logger.getLogger(Formatter.class.getCanonicalName());

	private FormatterParameters parameters;

	public Formatter(String[] args) throws ParseException {
		parameters = new FormatterParameters(args);
	}

	public static void main(String[] args) throws ParseException {
		MarcFileProcessor processor = new Formatter(args);
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

	@Override
	public void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		String message = String.format("java -cp metadata-qa-marc.jar %s [options] [file]", this.getClass().getCanonicalName());
		formatter.printHelp(message, options);
	}

	@Override
	public CommonParameters getParameters() {
		return parameters;
	}

	@Override
	public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {
		if ((marc4jRecord.getControlNumber() != null && marc4jRecord.getControlNumber().equals(parameters.getId()))
			|| (parameters.getCountNr() > -1 && parameters.getCountNr() == recordNumber)) {
			System.out.println(marc4jRecord.toString());
		}
		if (parameters.hasSearch()) {
			MarcRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord);
			List<String> results = marcRecord.search(parameters.getPath(), parameters.getQuery());
			if (!results.isEmpty()) {
				System.out.println(marc4jRecord.toString());
			}
		}
	}

	@Override
	public void processRecord(MarcRecord marcRecord, int recordNumber) throws IOException {

	}

	@Override
	public void beforeIteration() {

	}

	@Override
	public void fileOpened() {

	}

	@Override
	public void fileProcessed() {

	}

	@Override
	public void afterIteration() {

	}
}