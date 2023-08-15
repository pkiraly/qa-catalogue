package de.gwdg.metadataqa.marc.analysis.validator;

import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorCategory;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ValidatorDAO {

  private final Map<ValidationErrorCategory, Integer> categoryRecordCounter = new EnumMap<>(ValidationErrorCategory.class);
  private Map<String, Map<ValidationErrorCategory, Integer>> categoryRecordCounterGrouped = new HashMap<>();
  private final Map<ValidationErrorType, Integer> typeRecordCounter = new EnumMap<>(ValidationErrorType.class);
  private Map<String, Map<ValidationErrorType, Integer>> typeRecordCounterGrouped = new HashMap<>();
  private final Map<ValidationError, Integer> instanceBasedErrorCounter = new HashMap<>();
  private final Map<String, Map<ValidationError, Integer>> instanceBasedErrorCounterGrouped = new HashMap<>();
  private final Map<Integer, Integer> totalRecordCounter = new HashMap<>();
  private final Map<String, Map<Integer, Integer>> totalRecordCounterGrouped = new HashMap<>();
  private final Map<Integer, Integer> recordBasedErrorCounter = new HashMap<>();
  private final Map<String, Map<Integer, Integer>> recordBasedErrorCounterGrouped = new HashMap<>();
  private final Map<ValidationErrorCategory, Integer> categoryInstanceCounter = new EnumMap<>(ValidationErrorCategory.class);
  private final Map<String, Map<ValidationErrorCategory, Integer>> categoryInstanceCounterGrouped = new HashMap<>();
  private final Map<Integer, Integer> totalInstanceCounter = new HashMap<>();
  private final Map<Integer, Set<String>> errorCollector = new TreeMap<>();
  private final Map<ValidationErrorType, Integer> typeInstanceCounter = new EnumMap<>(ValidationErrorType.class);
  private final Map<String, Map<ValidationErrorType, Integer>> typeInstanceCounterGrouped = new HashMap<>();

  public Map<ValidationErrorCategory, Integer> getCategoryRecordCounter() {
    return categoryRecordCounter;
  }

  public Map<String, Map<ValidationErrorCategory, Integer>> getCategoryRecordCounterGrouped() {
    return categoryRecordCounterGrouped;
  }

  public Map<ValidationErrorType, Integer> getTypeRecordCounter() {
    return typeRecordCounter;
  }

  public Map<String, Map<ValidationErrorType, Integer>> getTypeRecordCounterGrouped() {
    return typeRecordCounterGrouped;
  }

  public Map<Integer, Integer> getTotalRecordCounter() {
    return totalRecordCounter;
  }

  public Map<String, Map<Integer, Integer>> getTotalRecordCounterGrouped() {
    return totalRecordCounterGrouped;
  }

  public Map<ValidationError, Integer> getInstanceBasedErrorCounter() {
    return instanceBasedErrorCounter;
  }

  public Map<String, Map<ValidationError, Integer>> getInstanceBasedErrorCounterGrouped() {
    return instanceBasedErrorCounterGrouped;
  }

  public Map<Integer, Integer> getTotalInstanceCounter() {
    return totalInstanceCounter;
  }

  public Map<Integer, Integer> getRecordBasedErrorCounter() {
    return recordBasedErrorCounter;
  }

  public Map<String, Map<Integer, Integer>> getRecordBasedErrorCounterGrouped() {
    return recordBasedErrorCounterGrouped;
  }

  public Map<Integer, Set<String>> getErrorCollector() {
    return errorCollector;
  }

  public Map<ValidationErrorType, Integer> getTypeInstanceCounter() {
    return typeInstanceCounter;
  }

  public Map<String, Map<ValidationErrorType, Integer>> getTypeInstanceCounterGrouped() {
    return typeInstanceCounterGrouped;
  }

  public Map<ValidationErrorCategory, Integer> getCategoryInstanceCounter() {
    return categoryInstanceCounter;
  }

  public Map<String, Map<ValidationErrorCategory, Integer>> getCategoryInstanceCounterGrouped() {
    return categoryInstanceCounterGrouped;
  }
}
