package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.analysis.functional.FrbrFunctionLister;
import de.gwdg.metadataqa.marc.analysis.functional.FunctionalAnalyzer;
import de.gwdg.metadataqa.marc.analysis.functional.Marc21FrbrFunctionLister;
import de.gwdg.metadataqa.marc.analysis.functional.Marc21FunctionalAnalyzer;
import de.gwdg.metadataqa.marc.analysis.functional.PicaFrbrFunctionLister;
import de.gwdg.metadataqa.marc.analysis.functional.PicaFunctionalAnalyzer;
import de.gwdg.metadataqa.marc.analysis.functional.UnimarcFrbrFunctionLister;
import de.gwdg.metadataqa.marc.analysis.functional.UnimarcFunctionalAnalyzer;
import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
import de.gwdg.metadataqa.marc.utils.Counter;
import de.gwdg.metadataqa.marc.utils.FunctionValue;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.marc4j.marc.Record;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.gwdg.metadataqa.marc.Utils.createRow;

public class FunctionalAnalysis extends QACli<CompletenessParameters> implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(FunctionalAnalysis.class.getCanonicalName());

  private final Options options;
  private final boolean readyToProcess;


  private FunctionalAnalyzer analyzer;
  private int recordNumber;

  public FunctionalAnalysis(String[] args) throws ParseException {
    parameters = new CompletenessParameters(args);
    options = parameters.getOptions();
    readyToProcess = true;
  }

  public static void main(String[] args) {
    BibliographicInputProcessor processor = null;
    try {
      processor = new FunctionalAnalysis(args);
    } catch (ParseException e) {
      logger.log(Level.SEVERE, "FunctionalAnalysis", e);
      System.exit(1);
    }
    if (processor.getParameters().getArgs().length < 1) {
      logger.severe("Please provide a MARC file name!");
      processor.printHelp(processor.getParameters().getOptions());
      System.exit(0);
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
  public CompletenessParameters getParameters() {
    return parameters;
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {
    // do nothing
  }

  @Override
  public void processRecord(BibliographicRecord bibliographicRecord, int recordNumber, List<ValidationError> errors) throws IOException {
    processRecord(bibliographicRecord, recordNumber);
  }

  @Override
  public void processRecord(BibliographicRecord bibliographicRecord, int recordNumber) throws IOException {
    if (parameters.getRecordIgnorator().isIgnorable(bibliographicRecord)) {
      return;
    }

    this.recordNumber = recordNumber;

    analyzer.consumeRecord(bibliographicRecord);
  }


  @Override
  public void beforeIteration() {
    // Determine the analyzer to be used
    if (parameters.isMarc21()) {
      FrbrFunctionLister marc21FrbrFunctionLister = new Marc21FrbrFunctionLister(parameters.getMarcVersion());
      analyzer = new Marc21FunctionalAnalyzer(marc21FrbrFunctionLister);
    } else if (parameters.isPica()) {
      FrbrFunctionLister picaFrbrFunctionLister = new PicaFrbrFunctionLister();
      analyzer = new PicaFunctionalAnalyzer(picaFrbrFunctionLister);
    } else if (parameters.isUnimarc()) {
      FrbrFunctionLister unimarcFrbrFunctionLister = new UnimarcFrbrFunctionLister();
      analyzer = new UnimarcFunctionalAnalyzer(unimarcFrbrFunctionLister);
    } else {
      throw new IllegalArgumentException("Unknown MARC format");
    }

    logger.info(() -> analyzer.getFrbrFunctionLister().getBaselineCounterMap().toString());
  }

  @Override
  public void fileOpened(Path path) {
    // do nothing
  }

  @Override
  public void fileProcessed() {
    // do nothing
  }

  @Override
  public void afterIteration(int numberOfprocessedRecords, long duration) {
    String fileExtension = ".csv";
    final char separator = getSeparator(parameters.getFormat());
    if (parameters.getFormat().equals(ValidationErrorFormat.TAB_SEPARATED)) {
      fileExtension = ".tsv";
    }

    Map<FRBRFunction, List<Double>> result = analyzer.percentOf(recordNumber);
    saveResult(result, fileExtension, separator);

    Map<FRBRFunction, Counter<FunctionValue>> percentHistogram = analyzer.getHistogram();
    saveHistogram(percentHistogram, fileExtension, separator);

    saveMapping(fileExtension, separator);
    saveParameters("functions.params.json", parameters, Map.of("numberOfprocessedRecords", numberOfprocessedRecords, "duration", duration));
  }

  private void saveMapping(String fileExtension,
                           char separator) {
    Map<FRBRFunction, List<String>> functions;
    functions = analyzer.getFrbrFunctionLister().getPathByFunction();

    var path = Paths.get(parameters.getOutputDir(), "functional-analysis-mapping" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write("frbrfunction" + separator + "count" + separator + "fields\n");
      for (FRBRFunction function : FRBRFunction.values()) {
        if (function.getParent() != null) {
          List<String> paths = functions != null ? functions.getOrDefault(function, new ArrayList<>()) : new ArrayList<>();
          List<Object> cells = new ArrayList<>();
          cells.add(function.toString());
          cells.add(paths.size());
          cells.add(StringUtils.join(paths, ";"));
          writer.write(createRow(cells));
        }
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, "afterIteration", e);
    }
  }

  private void saveHistogram(Map<FRBRFunction, Counter<FunctionValue>> histogram,
                             String fileExtension,
                             char separator) {
    logger.info("Functional analysis histogram");
    var path = Paths.get(
      parameters.getOutputDir(),
      "functional-analysis-histogram" + fileExtension
    );
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write("frbrfunction" + separator + "functioncount" + separator + "score" + separator + "count\n");
      histogram
        .entrySet()
        .stream()
        .forEach(entry -> {
          String function = entry.getKey().name();
          Map<FunctionValue, Integer> histogramOfFunction = entry.getValue().getMap();
          histogramOfFunction
            .keySet()
            .stream()
            .sorted((a, b) -> ((Integer) a.getCount()).compareTo(b.getCount()))
            .forEach(functionValue -> {
              Integer count = histogramOfFunction.get(functionValue);
              try {
                writer.write(createRow(function, functionValue.getCount(), functionValue.getPercentage(), count));
              } catch (IOException e) {
                logger.log(Level.SEVERE, "saveHistogram", e);
              }
            });
        });
    } catch (IOException e) {
      logger.log(Level.SEVERE, "saveHistogram", e);
    }
  }

  private void saveResult(Map<FRBRFunction, List<Double>> result,
                          String fileExtension,
                          char separator) {

    logger.info("Saving functional analysis");
    var path = Paths.get(parameters.getOutputDir(), "functional-analysis" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write("frbr-function" + separator + "avgcount" + separator + "avgscore\n");
      result
        .entrySet()
        .stream()
        .forEach(entry -> {
          try {
            List<Double> values = entry.getValue();
            writer.write(createRow(entry.getKey().name(), values.get(0), values.get(1)));
          } catch (IOException e) {
            logger.log(Level.SEVERE, "saveResult", e);
          }
        });
    } catch (IOException e) {
      logger.log(Level.SEVERE, "saveResult", e);
    }
  }

  // TODO: move it in a common class
  private char getSeparator(ValidationErrorFormat format) {
    if (format.equals(ValidationErrorFormat.TAB_SEPARATED)) {
      return '\t';
    } else {
      return ',';
    }
  }

  @Override
  public void printHelp(Options options) {
    // do nothing
  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }

  public FunctionalAnalyzer getAnalyzer() {
    return analyzer;
  }

}
