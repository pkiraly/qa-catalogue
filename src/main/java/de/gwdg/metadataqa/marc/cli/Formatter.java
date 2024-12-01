package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.cli.parameters.FormatterParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.MarcRecord;
import de.gwdg.metadataqa.marc.dao.record.PicaRecord;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.utils.SchemaSpec;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaSpec;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
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

  private final FormatterParameters parameters;
  private final boolean readyToProcess;
  private BufferedWriter writer;

  public Formatter(String[] args) throws ParseException {
    parameters = new FormatterParameters(args);
    readyToProcess = true;
  }

  public static void main(String[] args) {
    logger.severe(() -> "'" + StringUtils.join(args, "', '") + "'");
    try {
      BibliographicInputProcessor processor = new Formatter(args);
      if (processor.getParameters().getArgs().length < 1) {
        logger.severe("Please provide a MARC file name!");
        System.exit(1);
      }
      if (processor.getParameters().doHelp()) {
        processor.printHelp(processor.getParameters().getOptions());
        System.exit(0);
      }
      RecordIterator iterator = new RecordIterator(processor);
      logger.info(() -> processor.getParameters().formatParameters());
      iterator.start();
    } catch(Exception e) {
      logger.severe(() -> "ERROR. " + e.getLocalizedMessage());
      System.exit(1);
    }
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
    logger.info(parameters::formatParameters);

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
      try {
        writer.write(StringUtils.join(values, parameters.getSeparator()) + "\n");
      } catch (IOException e) {
        logger.log(Level.WARNING, "beforeIteration", e);
      }
    }
  }

  @Override
  public void fileOpened(Path file) {
    // do nothing
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {
    boolean hasSpecifiedId = parameters.hasId() &&
      marc4jRecord.getControlNumber() != null &&
      marc4jRecord.getControlNumber().trim().equals(parameters.getId());

    boolean hasSpecifiedRecordNumber = parameters.getCountNr() > -1
      && parameters.getCountNr() == recordNumber;

    if (hasSpecifiedId || hasSpecifiedRecordNumber) {
      logger.info(marc4jRecord::toString);
    }
  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber, List<ValidationError> errors) throws IOException {
    // do nothing
  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber) throws IOException {
    if (parameters.hasId() && marcRecord.getId().trim().equals(parameters.getId())) {
      for (DataField field : marcRecord.getDatafields()) {
       logger.info(field.getTag());
      }
      logger.info(() -> "has STA: " + marcRecord.hasDatafield("STA"));
    }

    if (parameters.hasSearch()) {
      List<String> results = marcRecord.search(parameters.getPath(), parameters.getQuery());
      if (!results.isEmpty()) {
        logger.info(marcRecord::toString);
      }
    }
    if (!parameters.hasSelector()) {
      return;
    }

    List<String> selectionResults = new ArrayList<>();
    if (parameters.withId()) {
      selectionResults.add(marcRecord.getId());
    }
    if (parameters.getSchemaType().equals(SchemaType.PICA)) {
      List<String> selectedPicaResults = selectPicaResults((PicaRecord) marcRecord);
      selectionResults.addAll(selectedPicaResults);
    } else {
      List<String> selectedMarcResults = selectMarcResults((MarcRecord) marcRecord);
      selectionResults.addAll(selectedMarcResults);
    }

    try {
      writer.write(StringUtils.join(selectionResults, parameters.getSeparator()) + "\n");
    } catch (IOException e) {
      logger.log(Level.SEVERE, "processRecord", e);
    }
  }

  @Override
  public void fileProcessed() {
    // do nothing
  }

  @Override
  public void afterIteration(int numberOfprocessedRecords, long duration) {
    if (writer == null) {
      return;
    }

    try {
      writer.close();
    } catch (IOException e) {
      logger.log(Level.SEVERE, "afterIteration", e);
    }
  }

  private List<String> selectPicaResults(PicaRecord picaRecord) {
    List<String> selectionResults = new ArrayList<>();
    for (SchemaSpec marcSpec : parameters.getSelector()) {
      PicaSpec spec = (PicaSpec) marcSpec;
      List<String> results = picaRecord.select(spec);

      if (results.isEmpty() || spec.getFunction() == null) {
        selectionResults.add(results.isEmpty() ? "" : StringUtils.join(results, "||"));
        continue;
      }

      List<String> candidates = new ArrayList<>();
      for (String result : results) {
        if (spec.getFunction().equals("extractPicaDate")) {
          candidates.add(extractPicaDate(result));
        }
      }
      results = candidates;
      selectionResults.add(results.isEmpty() ? "" : StringUtils.join(results, "||"));
    }
    return selectionResults;
  }

  private List<String> selectMarcResults(MarcRecord marcRecord) {
    List<String> selectionResults = new ArrayList<>();
    for (SchemaSpec marcSpec : parameters.getSelector()) {
      List<String> results = marcRecord.select(marcSpec);
      selectionResults.add(results.isEmpty() ? "" : StringUtils.join(results, "||"));
    }
    return selectionResults;
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
