package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.analysis.ThompsonTraillFields;
import de.gwdg.metadataqa.marc.analysis.ThompsonTraillAnalysis;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.ThompsonTraillCompletenessParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.dao.record.PicaRecord;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.marc4j.marc.Record;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.gwdg.metadataqa.marc.Utils.createRow;
import static de.gwdg.metadataqa.marc.Utils.quote;

/**
 * usage:
 * java -cp target/qa-catalogue-0.1-SNAPSHOT-jar-with-dependencies.jar \
 * de.gwdg.metadataqa.marc.cli.ThompsonTraillCompleteness [MARC21 file]
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class ThompsonTraillCompleteness extends QACli<ThompsonTraillCompletenessParameters> implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(
    ThompsonTraillCompleteness.class.getCanonicalName()
  );
  private final Options options;
  private final boolean readyToProcess;
  private File output = null;

  public ThompsonTraillCompleteness(String[] args) throws ParseException {
    parameters = new ThompsonTraillCompletenessParameters(args);
    System.err.println("tt().marcxml: " + parameters.isMarcxml());
    options = parameters.getOptions();
    readyToProcess = true;
  }

  public static void main(String[] args) {
    BibliographicInputProcessor processor = null;
    try {
      processor = new ThompsonTraillCompleteness(args);
    } catch (ParseException e) {
      System.err.println("ERROR. " + e.getLocalizedMessage());
      System.exit(1);
    }

    if (processor.getParameters().getArgs().length < 1) {
      System.err.println("Please provide a MARC file name!");
      System.exit(1);
    }
    if (processor.getParameters().doHelp()) {
      processor.printHelp(processor.getParameters().getOptions());
      System.exit(0);
    }

    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();
  }

  @Override
  public CommonParameters getParameters() {
    return parameters;
  }

  @Override
  public void beforeIteration() {
    logger.info(parameters.formatParameters());
    printFields();

    output = new File(parameters.getOutputDir(), parameters.getFileName());
    if (output.exists() && !output.delete())
      logger.severe("Deletion of " + output.getAbsolutePath() + " was unsuccessful!");

    print(createRow(ThompsonTraillAnalysis.getHeader()));
  }

  @Override
  public void fileOpened(Path path) {
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) {
    // do nothing
  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber, List<ValidationError> errors) throws IOException {
    // do nothing
  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber) {
    if (parameters.getRecordIgnorator().isIgnorable(marcRecord))
      return;

    List<Integer> scores = ThompsonTraillAnalysis.getScores(marcRecord);
    String id = parameters.getTrimId()
              ? marcRecord.getId().trim()
              : marcRecord.getId();
    String message = String.format(
      "\"%s\",%s%n",
      id, StringUtils.join(scores, ",")
    );
    print(message);
  }

  @Override
  public void fileProcessed() {

  }

  @Override
  public void afterIteration(int numberOfprocessedRecords) {
    saveParameters("tt-completeness.params.json", parameters);
  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }

  public void printHelp(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    String message = String.format(
      "java -cp qa-catalogue.jar %s [options] [file]",
      this.getClass().getCanonicalName()
    );
    formatter.printHelp(message, options);
  }

  private void print(String message) {
    try {
      FileUtils.writeStringToFile(output, message, Charset.defaultCharset(), true);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "print", e);
    }
  }

  private void printFields() {
    var path = Paths.get(parameters.getOutputDir(), "tt-completeness-fields.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("name", "transformed", "fields"));
      BibliographicRecord record = getParameters().getSchemaType().equals(SchemaType.MARC21) ? new Marc21Record() : new PicaRecord();
      Map<ThompsonTraillFields, List<String>> map = record.getThompsonTraillTagsMap();
      for (ThompsonTraillFields field : ThompsonTraillFields.values()) {
        try {
          writer.write(createRow(field.getLabel(), field.getMachine(), quote(StringUtils.join(map.getOrDefault(field, List.of()), ","))));
        } catch (IOException e) {
          logger.log(Level.SEVERE, "printFields", e);
        }
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, "printFields", e);
    }
  }
}
