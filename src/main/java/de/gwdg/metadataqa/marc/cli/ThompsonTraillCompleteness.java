package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.ThompsonTraillAnalysis;
import de.gwdg.metadataqa.marc.cli.parameters.ThompsonTraillCompletenessParameters;
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
import java.util.logging.Logger;

/**
 * usage:
 * java -cp target/metadata-qa-marc-0.1-SNAPSHOT-jar-with-dependencies.jar \
 * de.gwdg.metadataqa.marc.cli.ThompsonTraillCompleteness [MARC21 file]
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class ThompsonTraillCompleteness {

  private static final Logger logger = Logger.getLogger(ThompsonTraillCompleteness.class.getCanonicalName());

  public static void main(String[] args) throws ParseException {
    ThompsonTraillCompletenessParameters parameters = new ThompsonTraillCompletenessParameters(args);
    if (parameters.getArgs().length < 1) {
      System.err.println("Please provide a MARC file name!");
      System.exit(0);
    }
    if (parameters.doHelp()) {
      printHelp(parameters.getOptions());
      System.exit(0);
    }

    long start = System.currentTimeMillis();

    int limit = parameters.getLimit();
    if (parameters.doLog())
      logger.info("limit: " + limit);

    int offset = parameters.getOffset();
    if (parameters.doLog())
      logger.info("offset: " + offset);

    if (parameters.doLog())
      logger.info("MARC files: " + StringUtils.join(parameters.getArgs(), ", "));

    File output = new File(parameters.getFileName());
    if (output.exists())
      output.delete();

    String[] inputFileNames = parameters.getArgs();

    String message;
    int i = 0;
    for (String inputFileName : inputFileNames) {
      Path path = Paths.get(inputFileName);
      String fileName = path.getFileName().toString();

      if (parameters.doLog())
        logger.info("processing: " + fileName);

      try {
        if (i == 0) {
          message = StringUtils.join(ThompsonTraillAnalysis.getHeader(), ",") + "%n";
          FileUtils.writeStringToFile(output, message, true);
        }

        MarcReader reader = ReadMarc.getStreamReader(path.toString());
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
            List<Integer> scores = ThompsonTraillAnalysis.getScores(marcRecord);
            message = String.format("\"%s\",%s%n",
              marc4jRecord.getControlNumber(),
              StringUtils.join(scores, ","));
            FileUtils.writeStringToFile(output, message, true);

            if (i % 100000 == 0 && parameters.doLog())
              logger.info(String.format("%s/%d (id: %s)", fileName, i, marcRecord.getId()));
          } catch (IllegalArgumentException e) {
            if (parameters.doLog())
              logger.severe(String.format("Error with record '%s'. %s", marc4jRecord.getControlNumber(), e.getMessage()));
            continue;
          }
        }
        if (parameters.doLog())
          logger.info(String.format("End of cycle. Calculated %d records.", i));

      } catch (SolrServerException ex) {
        if (parameters.doLog())
          logger.severe(ex.toString());
        System.exit(0);
      } catch (Exception ex) {
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
    return offset > -1 && offset < i;
  }

  private static void printHelp(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("java -cp metadata-qa-marc.jar de.gwdg.metadataqa.marc.cli.Validator [options] [file]", options);
  }
}