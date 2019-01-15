package de.gwdg.metadataqa.marc.cli.spark;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.cli.parameters.ValidatorParameters;
import de.gwdg.metadataqa.marc.cli.Validator;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormatter;
import de.gwdg.metadataqa.marc.utils.ReadMarc;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.util.logging.Logger;

public class ParallelValidator {

	private static final Logger logger = Logger.getLogger(
		ParallelValidator.class.getCanonicalName());
	private static Options options = new Options();

	public static void main(String[] args) throws ParseException {

		final Validator validator = new Validator(args);
		ValidatorParameters params = validator.getParameters();
		validator.setDoPrintInProcessRecord(false);

		logger.info("Input file is " + params.getFileName());
		SparkConf conf = new SparkConf().setAppName("MarcCompletenessCount");
		JavaSparkContext context = new JavaSparkContext(conf);

		System.err.println(validator.getParameters().formatParameters());

		JavaRDD<String> inputFile = context.textFile(validator.getParameters().getArgs()[0]);

		JavaRDD<String> baseCountsRDD = inputFile
			.flatMap(content -> {
				MarcReader reader = ReadMarc.getMarcStringReader(content);
				Record marc4jRecord = reader.next();
				MarcRecord marcRecord = MarcFactory.createFromMarc4j(
					marc4jRecord, params.getDefaultRecordType(), params.getMarcVersion(), params.fixAlephseq());
				validator.processRecord(marcRecord, 1);
				return ValidationErrorFormatter
					.formatForSummary(marcRecord.getValidationErrors(), params.getFormat())
					.iterator();
			}
		);
		baseCountsRDD.saveAsTextFile(validator.getParameters().getFileName());
	}

	private static void help() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -cp [jar] de.gwdg.europeanaqa.spark.MarcCompletenessCount [options]", options);
	}
}
