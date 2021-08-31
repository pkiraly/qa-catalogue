package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.cli.utils.Schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ClassificationStatistics {
  private Map<Schema, Integer> instances = new HashMap<>();
  private Map<Schema, Integer> records = new HashMap<>();
  private Map<Schema, Map<List<String>, Integer>> subfields = new HashMap<>();
  private Map<String[], Integer> fieldInRecords = new HashMap<>();
  private Map<String, Map<String[], Integer>> fieldInstances = new TreeMap<>();
  private Map<Boolean, Integer> hasClassifications = new HashMap<>();
  private Map<Integer, Integer> schemaHistogram = new HashMap<>();
  private Map<List<String>, Integer> collocationHistogram = new HashMap<>();
  private Map<Integer, String> frequencyExamples = new HashMap<>();

  public ClassificationStatistics() {
  }

  public Map<Schema, Integer> getInstances() {
    return instances;
  }

  public Map<Schema, Integer> getRecords() {
    return records;
  }

  public Map<Schema, Map<List<String>, Integer>> getSubfields() {
    return subfields;
  }

  public Map<String[], Integer> getFieldInRecords() {
    return fieldInRecords;
  }

  public Map<String, Map<String[], Integer>> getFieldInstances() {
    return fieldInstances;
  }

  public Map<Boolean, Integer> getHasClassifications() {
    return hasClassifications;
  }

  public Map<Integer, Integer> getSchemaHistogram() {
    return schemaHistogram;
  }

  public Map<List<String>, Integer> getCollocationHistogram() {
    return collocationHistogram;
  }

  public Integer recordCountWithClassification() {
    if (collocationHistogram.isEmpty())
      return Integer.valueOf(0);
    return collocationHistogram
      .entrySet()
      .stream()
      .map(Map.Entry::getValue)
      .reduce((a, b) -> a + b)
      .get();
  }

  public Map<Integer, String> getFrequencyExamples() {
    return frequencyExamples;
  }
}
