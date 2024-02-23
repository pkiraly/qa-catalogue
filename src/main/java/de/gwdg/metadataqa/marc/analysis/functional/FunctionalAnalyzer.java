package de.gwdg.metadataqa.marc.analysis.functional;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.utils.Counter;
import de.gwdg.metadataqa.marc.utils.FunctionValue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Analyzes a record and counts the FRBR functions. In other words, it counts how many FRBR user tasks are
 * supported by the provided record.
 */
public abstract class FunctionalAnalyzer {

  /**
   * The map with the counts of the FRBR functions (user tasks) in one single record.
   */
  protected Map<FRBRFunction, FunctionValue> recordCounter;
  protected FrbrFunctionLister frbrFunctionLister;
  /**
   * The map with the counts of the FRBR functions (user tasks) in all records.
   */
  private final Map<FRBRFunction, FunctionValue> collector;
  private Map<FRBRFunction, Counter<FunctionValue>> histogram;

  protected FunctionalAnalyzer(FrbrFunctionLister frbrFunctionLister) {
    this.frbrFunctionLister = frbrFunctionLister;

    collector = initializeCounter();
    prepareHistogram();
  }

  /**
   * Consumes a bibliographic record. Calls the abstract method analyzeRecord() to analyze the record and count the
   * FRBR functions. Then, it adds the recordCounter to the collector and histogram maps.
   * @param bibliographicRecord The record to be analyzed and counted.
   */
  public void consumeRecord(BibliographicRecord bibliographicRecord) {
    recordCounter = initializeCounter();

    if (bibliographicRecord == null) {
      return;
    }

    analyzeRecord(bibliographicRecord);

    calculatePercentage(recordCounter);
    addRecordCounterToCollector(recordCounter);
    addRecordCounterToHistogram(recordCounter);
  }

  /**
   * Should be implemented to analyze a record and count the FRBR functions. It should create the recordCounter map
   * and return it. It is used in the method consumeRecord().
   *
   * @param bibliographicRecord The record to be analyzed and counted.
   */
  protected abstract void analyzeRecord(BibliographicRecord bibliographicRecord);

  protected abstract void countDataField(DataFieldDefinition definition,
                                         DataField dataField,
                                         Map<FRBRFunction, FunctionValue> recordCounter);

  protected final void countDataFields(Map<FRBRFunction, FunctionValue> functionCounter,
                                       List<DataField> dataFields,
                                       Map<DataFieldDefinition, Boolean> cache) {
    for (DataField dataField : dataFields) {
      DataFieldDefinition definition = dataField.getDefinition();
      if (cache.containsKey(definition)) {
        continue;
      }
      cache.putIfAbsent(definition, true);

      countDataField(definition, dataField, functionCounter);
    }
  }

  /**
   * Used to initialize a counter map with all FRBR functions and their respective values. This is intended to be used
   * for the recordCounter and collector maps.
   * @return A map with all FRBR functions and their respective values.
   */
  protected final Map<FRBRFunction, FunctionValue> initializeCounter() {
    Map<FRBRFunction, FunctionValue> emptyCounter = new TreeMap<>();
    for (FRBRFunction function : FRBRFunction.values()) {
      if (function.getParent() != null) {
        emptyCounter.put(function, new FunctionValue());
      }
    }
    return emptyCounter;
  }

  protected void prepareHistogram() {
    histogram = new TreeMap<>();
    for (FRBRFunction function : FRBRFunction.values()) {
      if (function.getParent() != null) {
        histogram.put(function, new Counter<>());
      }
    }
  }

  /**
   * Receives a list of functions (presumably from a data field, subfield or indicator) and counts them in the
   * recordCounter map.
   * @param functions The list of functions to be counted.
   * @param recordCounter The map where the functions will be counted. Format: Function -> FunctionValue (count and percentage).
   */
  public static void countFunctions(List<FRBRFunction> functions,
                                    Map<FRBRFunction, FunctionValue> recordCounter) {
    if (functions == null || functions.isEmpty()) {
      return;
    }

    for (FRBRFunction function : functions) {
      recordCounter.computeIfAbsent(function, s -> new FunctionValue());
      recordCounter.get(function).count();
    }
  }

  /**
   * Calculates the percentage of each function in the recordCounter map compared to the total count of
   * functions possible in the schema, represented by the baselineCounter of the frbrFunctionLister.
   * @param recordCounter The map with the counts of the FRBR functions of the current record.
   * @see FrbrFunctionLister
   */
  public void calculatePercentage(Map<FRBRFunction, FunctionValue> recordCounter) {
    Map<FRBRFunction, Integer> baselineCounterMap = frbrFunctionLister.getBaselineCounterMap();
    for (Map.Entry<FRBRFunction, FunctionValue> functionCountEntry : recordCounter.entrySet()) {
      FRBRFunction function = functionCountEntry.getKey();
      int totalCount = baselineCounterMap.getOrDefault(function, 0);
      recordCounter.get(function).calculatePercentage(totalCount);
    }
  }

  /**
   * Adds the recordCounter to the collector. The collector is a map that stores counts from all records.
   * @param recordCounter The map with the counts of the FRBR functions.
   */
  public void addRecordCounterToCollector(Map<FRBRFunction, FunctionValue> recordCounter) {
    for (Map.Entry<FRBRFunction, FunctionValue> entry : recordCounter.entrySet()) {
      collector.computeIfAbsent(entry.getKey(), s -> new FunctionValue());
      collector.get(entry.getKey()).add(entry.getValue());
    }
  }

  public void addRecordCounterToHistogram(Map<FRBRFunction, FunctionValue> recordCounter) {
    for (Map.Entry<FRBRFunction, FunctionValue> entry : recordCounter.entrySet()) {
      FRBRFunction function = entry.getKey();
      FunctionValue value = entry.getValue();
      histogram.computeIfAbsent(function, s -> new Counter<>());
      histogram.get(function).count(value);
    }
  }

  public Map<FRBRFunction, List<Double>> percentOf(int total) {
    Map<FRBRFunction, List<Double>> result = new TreeMap<>();
    for (Map.Entry<FRBRFunction, FunctionValue> entry : collector.entrySet()) {
      double avgCount = entry.getValue().getCount() * 1.0 / total;
      double avgPerc = entry.getValue().getPercentage() / total;
      result.put(entry.getKey(), Arrays.asList(avgCount, avgPerc));
    }
    return result;
  }

  public Map<FRBRFunction, FunctionValue> getCollector() {
    return collector;
  }

  public Map<FRBRFunction, Counter<FunctionValue>> getHistogram() {
    return histogram;
  }

  public FrbrFunctionLister getFrbrFunctionLister() {
    return frbrFunctionLister;
  }
}
