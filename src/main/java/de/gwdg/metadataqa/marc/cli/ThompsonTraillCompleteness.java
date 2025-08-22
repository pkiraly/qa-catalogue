package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.analysis.thompsontraill.Marc21ThompsonTraillAnalysis;
import de.gwdg.metadataqa.marc.analysis.thompsontraill.PicaThompsonTraillAnalysis;
import de.gwdg.metadataqa.marc.analysis.thompsontraill.ThompsonTraillAnalysis;
import de.gwdg.metadataqa.marc.analysis.thompsontraill.ThompsonTraillFields;
import de.gwdg.metadataqa.marc.analysis.thompsontraill.UnimarcThompsonTraillAnalysis;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.ThompsonTraillCompletenessParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.marc4j.marc.Record;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
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
  private ThompsonTraillAnalysis thompsonTraillAnalysis;

  public ThompsonTraillCompleteness(String[] args) throws ParseException {
    parameters = new ThompsonTraillCompletenessParameters(args);
    logger.info("tt().marcxml: " + parameters.isMarcxml());
    options = parameters.getOptions();
    // Create a ThompsonTraillAnalysis object based on the schema type
    setThompsonTraillAnalysis();

    readyToProcess = true;
  }

  public static void main(String[] args) {
    BibliographicInputProcessor processor = null;
    try {
      processor = new ThompsonTraillCompleteness(args);
    } catch (ParseException e) {
      logger.severe("ERROR. " + e.getLocalizedMessage());
      System.exit(1);
    }

    if (processor.getParameters().getArgs().length < 1) {
      logger.severe("Please provide a MARC file name!");
      System.exit(1);
    }
    if (processor.getParameters().doHelp()) {
      processor.printHelp(processor.getParameters().getOptions());
      System.exit(0);
    }

    RecordIterator iterator = new RecordIterator(processor);
    iterator.setProcessWithErrors(processor.getParameters().getProcessRecordsWithoutId());
    iterator.start();
  }

  @Override
  public CommonParameters getParameters() {
    return parameters;
  }

  @Override
  public void beforeIteration() {
    logger.info(() -> parameters.formatParameters());
    printFields();

    output = new File(parameters.getOutputDir(), parameters.getFileName());
    if (output.exists()) {
      try {
        Files.delete(output.toPath());
      } catch (IOException e) {
        logger.log(Level.SEVERE, "The output file ({}) has not been deleted", output.getAbsolutePath());
      }
    }
    print(createRow(thompsonTraillAnalysis.getHeader()));
  }

  @Override
  public void fileOpened(Path path) {
    // do nothing
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) {
    // do nothing
  }

  @Override
  public void processRecord(BibliographicRecord bibliographicRecord, int recordNumber, List<ValidationError> errors) throws IOException {
    processRecord(bibliographicRecord, recordNumber);
  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber) {
    if (parameters.getRecordIgnorator().isIgnorable(marcRecord))
      return;

    List<Integer> scores = thompsonTraillAnalysis.getScores(marcRecord);
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
    // The file processed method is not implemented.
  }

  @Override
  public void afterIteration(int numberOfprocessedRecords, long duration) {
    saveParameters("tt-completeness.params.json", parameters, Map.of("numberOfprocessedRecords", numberOfprocessedRecords, "duration", duration));
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
      FileUtils.writeStringToFile(output, message, StandardCharsets.UTF_8, true);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "print", e);
    }
  }

  private void printFields() {
    var path = Paths.get(parameters.getOutputDir(), "tt-completeness-fields.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("name", "transformed", "fields"));
      Map<ThompsonTraillFields, List<String>> map = thompsonTraillAnalysis.getThompsonTraillTagsMap();
      for (ThompsonTraillFields field : ThompsonTraillFields.values()) {
        writeFieldRow(writer, field, map);
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, "printFields", e);
    }
  }

  private void writeFieldRow(BufferedWriter writer, ThompsonTraillFields field, Map<ThompsonTraillFields, List<String>> map) {
    try {
      writer.write(createRow(field.getLabel(), field.getMachine(), quote(StringUtils.join(map.getOrDefault(field, List.of()), ","))));
    } catch (IOException e) {
      logger.log(Level.SEVERE, "printFields", e);
    }
  }

  private void setThompsonTraillAnalysis() {
    switch (parameters.getSchemaType()) {
      case PICA:
        thompsonTraillAnalysis = new PicaThompsonTraillAnalysis();
        break;
      case UNIMARC:
        thompsonTraillAnalysis = new UnimarcThompsonTraillAnalysis();
        break;
      case MARC21:
      default:
        thompsonTraillAnalysis = new Marc21ThompsonTraillAnalysis();
        break;
    }
  }
}
