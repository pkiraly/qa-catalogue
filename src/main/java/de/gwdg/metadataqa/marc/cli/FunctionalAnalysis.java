package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.*;
import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
import de.gwdg.metadataqa.marc.utils.FrbrFunctionLister;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.marc4j.marc.Record;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

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
    frbrFunctionLister = new FrbrFunctionLister();

    logger.info(frbrFunctionLister.getBaseline().toString());
  }

  public static void main(String[] args) {
    MarcFileProcessor processor = null;
    try {
      processor = new FunctionalAnalysis(args);
    } catch (ParseException e) {
      logger.severe("ERROR. " + e.getLocalizedMessage());
      e.printStackTrace();
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
    this.recordNumber = recordNumber;
    Map<FRBRFunction, Integer> recordCounter = new TreeMap<>();
    Map<DataFieldDefinition, Boolean> cache = new HashMap<>();

    countPositionalControlField(recordCounter, marcRecord.getLeader());
    countControlFields(recordCounter, marcRecord.getControlfields());
    countDataFields(recordCounter, marcRecord.getDatafields(), cache);

    Map<FRBRFunction, Double> percent = frbrFunctionLister.percent(recordCounter);
    frbrFunctionLister.add(percent);
    frbrFunctionLister.addToHistogram(percent);
  }

  private void countDataFields(Map<FRBRFunction, Integer> recordCounter,
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

  private void countIndicator(Map<FRBRFunction, Integer> recordCounter,
                              Indicator definition,
                              String value) {
    if (definition.getFrbrFunctions() != null
        && StringUtils.isNotBlank(value)) {
      FrbrFunctionLister.countFunctions(
        definition.getFrbrFunctions(), recordCounter);
    }
  }

  private void countControlFields(Map<FRBRFunction, Integer> recordCounter,
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

  private void countPositionalControlField(Map<FRBRFunction, Integer> recordCounter,
                                           MarcPositionalControlField leader) {
    for (ControlValue controlValue : leader.getValuesList()) {
      FrbrFunctionLister.countFunctions(
        controlValue.getDefinition().getFrbrFunctions(), recordCounter
      );
    }
  }

  @Override
  public void beforeIteration() {

  }

  @Override
  public void fileOpened(Path path) {

  }

  @Override
  public void fileProcessed() {

  }

  @Override
  public void afterIteration(int numberOfprocessedRecords) {
    // DecimalFormat format = new DecimalFormat();
    String fileExtension = ".csv";
    final char separator = getSeparator(parameters.getFormat());
    if (parameters.getFormat().equals(ValidationErrorFormat.TAB_SEPARATED)) {
      fileExtension = ".tsv";
    }

    Map<FRBRFunction, Double> result = frbrFunctionLister.percentOf(recordNumber);
    saveResult(result, fileExtension, separator);

    Map<FRBRFunction, Map<Double, Integer>> histogram = frbrFunctionLister.getHistogram();
    saveHistogram(histogram, fileExtension, separator);
  }

  private void saveHistogram(Map<FRBRFunction, Map<Double, Integer>> histogram,
                             String fileExtension,
                             char separator) {
    System.err.println("Functional analysis histogram");
    Path path = Paths.get(
      parameters.getOutputDir(),
      "functional-analysis-histogram" + fileExtension
    );
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      writer.write("frbrfunction" + separator + "score" + separator + "count\n");
      histogram
        .entrySet()
        .stream()
        .forEach(entry -> {
          try {
            String function = entry.getKey().name();
            Map<Double, Integer> histogramOfFunction = entry.getValue();
            for (Map.Entry<Double, Integer> histogramEntry : histogramOfFunction.entrySet()) {
              Double score = histogramEntry.getKey();
              Integer count = histogramEntry.getValue();
              writer.write(
                StringUtils.join(
                  Arrays.asList(function, score, count),
                  separator
                ) + "\n"
              );
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void saveResult(Map<FRBRFunction, Double> result,
                          String fileExtension,
                          char separator) {

    System.err.println("Functional analysis");
    Path path = Paths.get(parameters.getOutputDir(), "functional-analysis" + fileExtension);
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      writer.write("frbr-function" + separator + "score\n");
      result
        .entrySet()
        .stream()
        .forEach(entry -> {
          try {
            writer.write(String.format("\"%s\"%s%f%n", entry.getKey().name(), separator, entry.getValue()));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    } catch (IOException e) {
      e.printStackTrace();
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
