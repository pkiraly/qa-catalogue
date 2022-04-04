package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.*;
import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcControlField;
import de.gwdg.metadataqa.marc.dao.MarcPositionalControlField;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
import de.gwdg.metadataqa.marc.utils.Counter;
import de.gwdg.metadataqa.marc.utils.FrbrFunctionLister;
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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.gwdg.metadataqa.marc.Utils.createRow;

public class FunctionalAnalysis implements MarcFileProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(FunctionalAnalysis.class.getCanonicalName());

  private final Options options;
  private final boolean readyToProcess;
  private final CompletenessParameters parameters;
  private FrbrFunctionLister frbrFunctionLister;
  private int recordNumber;

  public FunctionalAnalysis(String[] args) throws ParseException {
    parameters = new CompletenessParameters(args);
    options = parameters.getOptions();
    readyToProcess = true;
    frbrFunctionLister = new FrbrFunctionLister(parameters.getMarcVersion());

    logger.info(frbrFunctionLister.getBaseline().toString());
  }

  public static void main(String[] args) {
    MarcFileProcessor processor = null;
    try {
      processor = new FunctionalAnalysis(args);
    } catch (ParseException e) {
      logger.log(Level.SEVERE, "FunctionalAnalysis", e);
      System.exit(0);
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
    iterator.start();
  }

  @Override
  public CompletenessParameters getParameters() {
    return parameters;
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {

  }

  @Override
  public void processRecord(MarcRecord marcRecord, int recordNumber) throws IOException {
    if (parameters.getIgnorableRecords().isIgnorable(marcRecord))
      return;

    this.recordNumber = recordNumber;
    Map<FRBRFunction, FunctionValue> recordCounter = new TreeMap<>();
    for (FRBRFunction f : FRBRFunction.values())
      if (f.getParent() != null)
        recordCounter.put(f, new FunctionValue());

    Map<DataFieldDefinition, Boolean> cache = new HashMap<>();

    countPositionalControlField(recordCounter, marcRecord.getLeader());
    countControlFields(recordCounter, marcRecord.getControlfields());
    countDataFields(recordCounter, marcRecord.getDatafields(), cache);

    frbrFunctionLister.calculatePercent(recordCounter);

    frbrFunctionLister.add(recordCounter);
    frbrFunctionLister.addToHistogram(recordCounter);
  }

  private void countDataFields(Map<FRBRFunction, FunctionValue> recordCounter,
                               List<DataField> dataFields,
                               Map<DataFieldDefinition, Boolean> cache) {
    for (DataField dataField : dataFields) {
      DataFieldDefinition definition = dataField.getDefinition();
      if (!cache.containsKey(definition)) {
        cache.put(definition, true);
        if (definition != null) {
          countIndicator(recordCounter, definition.getInd1(), dataField.getInd1());
          countIndicator(recordCounter, definition.getInd2(), dataField.getInd2());
        }
        for (MarcSubfield subfield : dataField.getSubfields()) {
          if (subfield.getDefinition() != null
              && subfield.getDefinition().getFrbrFunctions() != null) {
            FrbrFunctionLister.countFunctions(
              subfield.getDefinition().getFrbrFunctions(), recordCounter);
          }
        }
      }
    }
  }

  private void countIndicator(Map<FRBRFunction, FunctionValue> recordCounter,
                              Indicator definition,
                              String value) {
    if (definition.getFrbrFunctions() != null
        && StringUtils.isNotBlank(value)) {
      FrbrFunctionLister.countFunctions(
        definition.getFrbrFunctions(), recordCounter);
    }
  }

  private void countControlFields(Map<FRBRFunction, FunctionValue> recordCounter,
                                  List<MarcControlField> controlFields) {
    for (MarcControlField controlField : controlFields) {
      if (controlField == null) {
        continue;
      }
      if (controlField instanceof MarcPositionalControlField) {
        countPositionalControlField(recordCounter, (MarcPositionalControlField) controlField);
      } else {
        FrbrFunctionLister.countFunctions(
          controlField.getDefinition().getFrbrFunctions(), recordCounter
        );
      }
    }
  }

  private void countPositionalControlField(Map<FRBRFunction, FunctionValue> recordCounter,
                                           MarcPositionalControlField leader) {
    for (ControlValue controlValue : leader.getValuesList()) {
      FrbrFunctionLister.countFunctions(
        controlValue.getDefinition().getFrbrFunctions(), recordCounter
      );
    }
  }

  @Override
  public void beforeIteration() {
    // do nothing
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
  public void afterIteration(int numberOfprocessedRecords) {
    String fileExtension = ".csv";
    final char separator = getSeparator(parameters.getFormat());
    if (parameters.getFormat().equals(ValidationErrorFormat.TAB_SEPARATED)) {
      fileExtension = ".tsv";
    }

    Map<FRBRFunction, List<Double>> result = frbrFunctionLister.percentOf(recordNumber);
    saveResult(result, fileExtension, separator);

    Map<FRBRFunction, Counter<FunctionValue>> percentHistogram = frbrFunctionLister.getHistogram();
    saveHistogram(percentHistogram, fileExtension, separator);

    saveMapping(fileExtension, separator);
  }

  private void saveMapping(String fileExtension,
                           char separator) {
    Map<FRBRFunction, List<String>> functions = frbrFunctionLister.getMarcPathByfunction();
    var path = Paths.get(parameters.getOutputDir(), "functional-analysis-mapping" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write("frbrfunction" + separator + "count" + separator + "fields\n");
      for (FRBRFunction function : FRBRFunction.values()) {
        if (function.getParent() != null) {
          List<String> paths = functions.get(function);
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
                writer.write(createRow(function, functionValue.getCount(), functionValue.getPercent(), count));
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

  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }
}
