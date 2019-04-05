package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.*;
import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
import de.gwdg.metadataqa.marc.definition.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
import de.gwdg.metadataqa.marc.utils.FrbrFunctionLister;
import de.gwdg.metadataqa.marc.utils.marcspec.Subfield;
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
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class FunctionalAnalysis implements MarcFileProcessor, Serializable {

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
  }

  public static void main(String[] args) {
    MarcFileProcessor processor = null;
    try {
      processor = new FunctionalAnalysis(args);
    } catch (ParseException e) {
      System.err.println("ERROR. " + e.getLocalizedMessage());
      System.exit(0);
    }
    if (processor.getParameters().getArgs().length < 1) {
      System.err.println("Please provide a MARC file name!");
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

    for (ControlValue controlValue : marcRecord.getLeader().getValuesList()) {
      FrbrFunctionLister.countFunctions(controlValue.getDefinition().getFrbrFunctions(), recordCounter);
    }

    for (MarcControlField controlField : marcRecord.getControlfields()) {
      if (controlField == null) {
        continue;
      }
      if (controlField instanceof MarcPositionalControlField) {
        MarcPositionalControlField positionalControlField = (MarcPositionalControlField) controlField;
        for (ControlValue controlValue : positionalControlField.getValuesList()) {
          FrbrFunctionLister.countFunctions(controlValue.getDefinition().getFrbrFunctions(), recordCounter);
        }
      } else {
        FrbrFunctionLister.countFunctions(controlField.getDefinition().getFrbrFunctions(), recordCounter);
      }
    }

    for (DataField dataField : marcRecord.getDatafields()) {
      DataFieldDefinition definition = dataField.getDefinition();
      if (!cache.containsKey(definition)) {
        cache.put(definition, true);
        if (definition.getInd1().getFrbrFunctions() != null
            && StringUtils.isNotBlank(dataField.getInd1())) {
          FrbrFunctionLister.countFunctions(definition.getInd1().getFrbrFunctions(), recordCounter);
        }
        if (definition.getInd2().getFrbrFunctions() != null
            && StringUtils.isNotBlank(dataField.getInd2())) {
          FrbrFunctionLister.countFunctions(definition.getInd2().getFrbrFunctions(), recordCounter);
        }
        for (MarcSubfield subfield : dataField.getSubfields()) {
          if (subfield.getDefinition() != null && subfield.getDefinition().getFrbrFunctions() != null) {
            FrbrFunctionLister.countFunctions(subfield.getDefinition().getFrbrFunctions(), recordCounter);
          }
        }
      }
    }

    Map<FRBRFunction, Double> percent = frbrFunctionLister.percent(recordCounter);
    frbrFunctionLister.add(percent);
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
  public void afterIteration() {
    Map<FRBRFunction, Double> result = frbrFunctionLister.percentOf(recordNumber);

    DecimalFormat format = new DecimalFormat();
    String fileExtension = ".csv";
    final char separator = getSeparator(parameters.getFormat());
    if (parameters.getFormat().equals(ValidationErrorFormat.TAB_SEPARATED)) {
      fileExtension = ".tsv";
    }

    saveResult(result, fileExtension, separator);
  }

  private void saveResult(Map<FRBRFunction, Double> result, String fileExtension, char separator) {
    Path path;
    System.err.println("Functional analysis");
    path = Paths.get(parameters.getOutputDir(), "functional-analysis" + fileExtension);
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
