package de.gwdg.metadataqa.marc.cli.spark;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.analysis.validator.Validator;
import de.gwdg.metadataqa.marc.analysis.validator.ValidatorConfiguration;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.cli.parameters.ValidatorParameters;
import de.gwdg.metadataqa.marc.cli.ValidatorCli;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormatter;
import de.gwdg.metadataqa.marc.utils.QAMarcReaderFactory;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
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

  public static void main(String[] args) {

    JavaSparkContext context = null;
    try {
      final ValidatorCli validatorCli = new ValidatorCli(args);
      ValidatorParameters params = validatorCli.getParameters();
      final ValidatorConfiguration validatorConfiguration = validatorCli.getValidityConfiguration();
      validatorCli.setDoPrintInProcessRecord(false);

      logger.info("Input file is " + params.getDetailsFileName());
      SparkConf conf = new SparkConf().setAppName("MarcCompletenessCount");

      context = new JavaSparkContext(conf);
      System.err.println(validatorCli.getParameters().formatParameters());

      JavaRDD<String> inputFile = context.textFile(validatorCli.getParameters().getArgs()[0]);
      JavaRDD<String> baseCountsRDD = inputFile
        .flatMap(content -> {
            MarcReader reader = QAMarcReaderFactory.getStringReader(MarcFormat.ISO, content);
            Record marc4jRecord = reader.next();
            BibliographicRecord marcRecord = MarcFactory.createFromMarc4j(
              marc4jRecord, params.getDefaultRecordType(), params.getMarcVersion(), params.getReplacementInControlFields());
            validatorCli.processRecord(marcRecord, 1);
            Validator analyzer = new Validator(validatorConfiguration);
            analyzer.validate(marcRecord);
            return ValidationErrorFormatter
              .formatForSummary(analyzer.getValidationErrors(), params.getFormat())
              .iterator();
          }
        );
      baseCountsRDD.saveAsTextFile(validatorCli.getParameters().getDetailsFileName());
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    } finally {
      if (context != null)
        context.close();
    }
  }

  private static void help() {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("java -cp [jar] de.gwdg.europeanaqa.spark.MarcCompletenessCount [options]", options);
  }
}
