package de.gwdg.metadataqa.marc.analysis.contextual.classification;

import de.gwdg.metadataqa.marc.analysis.contextual.ContextualStatistics;
import de.gwdg.metadataqa.marc.cli.utils.Schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class ClassificationStatistics extends ContextualStatistics {
  private final Map<String[], Integer> fieldInRecords = new HashMap<>();
  private final Map<String, Map<String[], Integer>> fieldInstances = new TreeMap<>();
  private final Map<Boolean, Integer> hasClassifications = new HashMap<>();
  private final Map<Integer, Integer> schemaHistogram = new HashMap<>();

  /**
   * Map of list of schema abbreviations (see {@link Schema}) and the number of records that have those schemas.
   */
  private final Map<List<String>, Integer> collocationHistogram = new HashMap<>();
  private final Map<Integer, String> frequencyExamples = new HashMap<>();

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
    return collocationHistogram
      .values()
      .stream()
      .reduce(Integer::sum)
      .orElse(0);
  }

  public Map<Integer, String> getFrequencyExamples() {
    return frequencyExamples;
  }
}
