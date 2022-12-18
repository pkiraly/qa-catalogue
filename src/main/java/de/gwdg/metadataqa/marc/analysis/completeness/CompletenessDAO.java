package de.gwdg.metadataqa.marc.analysis.completeness;

import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CompletenessDAO {

  private Map<String, Map<String, Integer>> packageCounter = new TreeMap<>();
  private Map<String, Map<String, Map<String, Integer>>> grouppedPackageCounter = new TreeMap<>();
  private Map<String, Map<String, Integer>> elementCardinality = new TreeMap<>();
  private Map<String, Map<String, Map<String, Integer>>> grouppedElementCardinality = new TreeMap<>();
  private Map<String, Map<String, Integer>> elementFrequency = new TreeMap<>();
  private Map<String, Integer> groupCounter = new TreeMap<>();
  private Map<String, Integer> library003Counter = new TreeMap<>();
  private Map<String, Integer> libraryCounter = new TreeMap<>();
  private Map<DataFieldDefinition, String> packageNameCache = new HashMap<>();

  public void initialize() {
    packageCounter.put("all", new TreeMap<>());
    elementCardinality.put("all", new TreeMap<>());
    elementFrequency.put("all", new TreeMap<>());
  }

  public Map<String, Map<String, Integer>> getPackageCounter() {
    return packageCounter;
  }

  public Map<String, Map<String, Map<String, Integer>>> getGrouppedPackageCounter() {
    return grouppedPackageCounter;
  }

  public Map<String, Map<String, Integer>> getElementCardinality() {
    return elementCardinality;
  }

  public Map<String, Map<String, Map<String, Integer>>> getGrouppedElementCardinality() {
    return grouppedElementCardinality;
  }

  public Map<String, Map<String, Integer>> getElementFrequency() {
    return elementFrequency;
  }

  public Map<String, Integer> getGroupCounter() {
    return groupCounter;
  }

  public Map<String, Integer> getLibrary003Counter() {
    return library003Counter;
  }

  public Map<String, Integer> getLibraryCounter() {
    return libraryCounter;
  }

  public Map<DataFieldDefinition, String> getPackageNameCache() {
    return packageNameCache;
  }
}
