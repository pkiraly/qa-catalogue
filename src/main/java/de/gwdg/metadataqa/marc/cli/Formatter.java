package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.cli.parameters.FormatterParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.utils.SchemaSpec;
import de.gwdg.metadataqa.marc.utils.marcspec.legacy.MarcSpec;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaSpec;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.marc4j.marc.Record;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * usage:
 * java -cp target/qa-catalogue-0.1-SNAPSHOT-jar-with-dependencies.jar de.gwdg.metadataqa.marc.cli.Validator [MARC21 file]
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Formatter implements BibliographicInputProcessor {

  private static final Logger logger = Logger.getLogger(Formatter.class.getCanonicalName());

  private FormatterParameters parameters;
  private boolean readyToProcess;
  private BufferedWriter writer;

  public Formatter(String[] args) throws ParseException {
    parameters = new FormatterParameters(args);
    readyToProcess = true;
  }

  public static void main(String[] args) throws ParseException {
    System.err.println("'" + StringUtils.join(args, "', '") + "'");
    BibliographicInputProcessor processor = new Formatter(args);
    if (processor.getParameters().getArgs().length < 1) {
      System.err.println("Please provide a MARC file name!");
      System.exit(1);
    }
    if (processor.getParameters().doHelp()) {
      processor.printHelp(processor.getParameters().getOptions());
      System.exit(0);
    }
    RecordIterator iterator = new RecordIterator(processor);
    logger.info(processor.getParameters().formatParameters());
    iterator.start();
  }

  @Override
  public void printHelp(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    String message = String.format("java -cp qa-catalogue.jar %s [options] [file]", this.getClass().getCanonicalName());
    formatter.printHelp(message, options);
  }

  @Override
  public FormatterParameters getParameters() {
    return parameters;
  }

  @Override
  public void beforeIteration() {
    logger.info(parameters.formatParameters());

    // print headers
    if (parameters.hasSelector()) {
      var path = Paths.get(parameters.getOutputDir(), parameters.getFileName());
      try {
        writer = Files.newBufferedWriter(path);
      } catch (IOException e) {
        logger.log(Level.WARNING, "beforeIteration", e);
      }
      List<String> values = new ArrayList<>();
      if (parameters.withId())
        values.add("id");
      for (SchemaSpec spec : parameters.getSelector()) {
        values.add(spec.encode());
      }
      // System.out.println(StringUtils.join(values, parameters.getSeparator()));
      try {
        writer.write(StringUtils.join(values, parameters.getSeparator()) + "\n");
      } catch (IOException e) {
        logger.log(Level.WARNING, "beforeIteration", e);
      }
    }
  }

  @Override
  public void fileOpened(Path file) {

  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {
    if (
      (parameters.hasId()
        && marc4jRecord.getControlNumber() != null
        && marc4jRecord.getControlNumber().trim().equals(parameters.getId())
      )
        ||
        (
          parameters.getCountNr() > -1
            && parameters.getCountNr() == recordNumber)) {
      System.out.println(marc4jRecord.toString());
    }
  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber) throws IOException {
    if (parameters.hasId() && marcRecord.getId().trim().equals(parameters.getId())) {
      for (DataField field : marcRecord.getDatafields()) {
        System.err.println(field.getTag());
      }
      System.err.println("has STA: " + marcRecord.hasDatafield("STA"));
    }

    if (parameters.hasSearch()) {
      List<String> results = marcRecord.search(parameters.getPath(), parameters.getQuery());
      if (!results.isEmpty()) {
        System.out.println(marcRecord.toString());
      }
    }
    if (parameters.hasSelector()) {
      List<String> values = new ArrayList<>();
      if (parameters.withId())
        values.add(marcRecord.getId());
      if (parameters.getSchemaType().equals(SchemaType.MARC21)) {
        for (SchemaSpec marcSpec : parameters.getSelector()) {
          List<String> results = marcRecord.select((MarcSpec) marcSpec);
          values.add(results.isEmpty() ? "" : StringUtils.join(results, "||"));
        }
      } else if (parameters.getSchemaType().equals(SchemaType.PICA)) {
        for (SchemaSpec marcSpec : parameters.getSelector()) {
          PicaSpec spec = (PicaSpec) marcSpec;
          List<String> results = marcRecord.select(spec.getPath());
          if (!results.isEmpty() && spec.getFunction() != null) {
            List<String> candidates = new ArrayList<>();
            for (String result : results)
              if (spec.getFunction().equals("extractPicaDate"))
                candidates.add(extractPicaDate(result));
            results = candidates;
          }
          values.add(results.isEmpty() ? "" : StringUtils.join(results, "||"));
        }
      }
      // System.out.println(StringUtils.join(values, parameters.getSeparator()));
      try {
        writer.write(StringUtils.join(values, parameters.getSeparator()) + "\n");
      } catch (IOException e) {
        logger.log(Level.SEVERE, "processRecord", e);
      }
    }
  }

  @Override
  public void fileProcessed() {

  }

  @Override
  public void afterIteration(int numberOfprocessedRecords) {
    if (writer != null) {
      try {
        writer.close();
      } catch (IOException e) {
        logger.log(Level.SEVERE, "afterIteration", e);
      }
    }
  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }

  public static String extractPicaDate(String dateInString) {
    String[] parts1 = dateInString.split(":", 2);
    String[] dateParts = parts1[1].split("-");
    return dateParts[2] + dateParts[1] + dateParts[0];
    // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yy", Locale.ENGLISH);
    // LocalDate dateTime = LocalDate.parse(dateInString, formatter);
    // return dateTime;
  }
}
