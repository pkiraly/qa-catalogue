package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.Leader;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.utils.ReadMarc;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.solr.client.solrj.SolrServerException;
import org.marc4j.MarcException;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.logging.Logger;

/**
 * usage:
 * java -cp target/metadata-qa-marc-0.1-SNAPSHOT-jar-with-dependencies.jar de.gwdg.metadataqa.marc.cli.Validator [MARC21 file]
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class RecordIterator {

  private static final Logger logger = Logger.getLogger(RecordIterator.class.getCanonicalName());
  private static Options options;
  private MarcFileProcessor processor;

  public RecordIterator(MarcFileProcessor processor) {
    this.processor = processor;
  }

  public void start() {

    long start = System.currentTimeMillis();
    processor.beforeIteration();

    MarcVersion marcVersion = processor.getParameters().getMarcVersion();
    Leader.Type defaultRecordType = processor.getParameters().getDefaultRecordType();
    boolean fixAlephseq = processor.getParameters().fixAlephseq();
    boolean isMarcxml = processor.getParameters().isMarcxml();
    boolean isLineSeparated = processor.getParameters().isLineSeparated();
    DecimalFormat decimalFormat = new DecimalFormat();

    if (processor.getParameters().doLog())
      logger.info("marcVersion: " + marcVersion.getCode() + ", " + marcVersion.getLabel());

    String[] inputFileNames = processor.getParameters().getArgs();

    int i = 0;
    String lastKnownId = "";
    for (String inputFileName : inputFileNames) {
      if (!processor.readyToProcess())
        break;

      Path path = Paths.get(inputFileName);
      String fileName = path.getFileName().toString();

      if (processor.getParameters().doLog())
        logger.info("processing: " + fileName);

      try {
        processor.fileOpened(path);
        MarcReader reader = ReadMarc.getReader(path.toString(), isMarcxml, isLineSeparated);
        while (reader.hasNext()) {
          if (!processor.readyToProcess())
            break;

          Record marc4jRecord = null;
          try {
            marc4jRecord = reader.next();
          } catch (MarcException | NegativeArraySizeException | NumberFormatException e) {
            logger.severe(
              String.format(
                "MARC record parsing problem at record #%d (last known ID: %s): %s",
                (i+1), lastKnownId, e.getLocalizedMessage()));
          } catch (Exception e) {
            logger.severe("another exception");
            e.printStackTrace();
          }
          i++;
          if (marc4jRecord == null)
            continue;

          if (isUnderOffset(processor.getParameters().getOffset(), i)) {
            continue;
          }
          if (isOverLimit(processor.getParameters().getLimit(), i)) {
            break;
          }

          if (marc4jRecord.getControlNumber() == null) {
            logger.severe("No record number at " + i + ", last known ID: " + lastKnownId);
            System.err.println(marc4jRecord);
            continue;
          } else {
            lastKnownId = marc4jRecord.getControlNumber();
          }

          if (processor.getParameters().hasId()
            && !marc4jRecord.getControlNumber().trim().equals(processor.getParameters().getId())) {
            continue;
          }

          boolean p1 = false;
          boolean p2 = false;
          boolean p3 = false;
          boolean p4 = false;
          try {
            p1 = true;
            processor.processRecord(marc4jRecord, i);
            p2 = true;
            MarcRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord, defaultRecordType, marcVersion, fixAlephseq);
            p3 = true;
            try {
              processor.processRecord(marcRecord, i);
            } catch(Exception e) {
              e.printStackTrace();
            }
            p4 = true;

            if (i % 100000 == 0 && processor.getParameters().doLog())
              logger.info(String.format("%s/%s (%s)", fileName, decimalFormat.format(i), marcRecord.getId()));

          } catch (IllegalArgumentException e) {
            if (marc4jRecord.getControlNumber() == null)
              logger.severe("No record number at " + i);
            if (processor.getParameters().doLog())
              logger.severe(String.format("Error (illegal argument) with record '%s'. %s", marc4jRecord.getControlNumber(), e.getMessage()));
            e.printStackTrace();
            continue;
          } catch (Exception e) {
            e.printStackTrace();
            System.err.printf("1: %s, 2: %s, 3: %s, 4: %s\n", p1, p2, p3, p4);
            if (marc4jRecord.getControlNumber() == null)
              logger.severe("No record number at " + i);
            if (processor.getParameters().doLog())
              logger.severe(String.format("Error (general) with record '%s'. %s", marc4jRecord.getControlNumber(), e.getMessage()));
            continue;
          }
        }
        if (processor.getParameters().doLog())
          logger.info(String.format("Finished processing file. Processed %d records.", i));

      } catch (SolrServerException ex){
        if (processor.getParameters().doLog())
          logger.severe(ex.toString());
        System.exit(0);
      } catch (Exception ex){
        if (processor.getParameters().doLog()) {
          logger.severe("Other exception: " + ex.toString());

          for (StackTraceElement element : ex.getStackTrace()) {
            System.err.println(element.toString());
          }
          Throwable exa = ex;
          while (exa.getCause() != null) {
            System.err.println("cause");
            exa = exa.getCause();
            for (StackTraceElement element : exa.getStackTrace()) {
              System.err.println(element.toString());
            }
          }
        }
        ex.printStackTrace();
        System.exit(0);
      }
    }

    processor.afterIteration();

    long end = System.currentTimeMillis();
    long duration = (end - start) / 1000;
    if (processor.getParameters().doLog())
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