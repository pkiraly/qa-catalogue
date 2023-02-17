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
  private final Map<ValidationErrorType, Integer> typeRecordCounter = new EnumMap<>(ValidationErrorType.class);
  private final Map<Integer, Integer> totalRecordCounter = new HashMap<>();
  private final Map<ValidationError, Integer> instanceBasedErrorCounter = new HashMap<>();
  private final Map<String, Map<ValidationError, Integer>> grouppedInstanceBasedErrorCounter = new HashMap<>();
  private final Map<Integer, Integer> totalInstanceCounter = new HashMap<>();
  private final Map<Integer, Integer> recordBasedErrorCounter = new HashMap<>();
  private final Map<Integer, Set<String>> errorCollector = new TreeMap<>();
  private final Map<ValidationErrorType, Integer> typeInstanceCounter = new EnumMap<>(ValidationErrorType.class);
  private final Map<ValidationErrorCategory, Integer> categoryInstanceCounter = new EnumMap<>(ValidationErrorCategory.class);

  public Map<ValidationErrorCategory, Integer> getCategoryRecordCounter() {
    return categoryRecordCounter;
  }

  public Map<ValidationErrorType, Integer> getTypeRecordCounter() {
    return typeRecordCounter;
  }

  public Map<Integer, Integer> getTotalRecordCounter() {
    return totalRecordCounter;
  }

  public Map<ValidationError, Integer> getInstanceBasedErrorCounter() {
    return instanceBasedErrorCounter;
  }

  public Map<String, Map<ValidationError, Integer>> getGrouppedInstanceBasedErrorCounter() {
    return grouppedInstanceBasedErrorCounter;
  }

  public Map<Integer, Integer> getTotalInstanceCounter() {
    return totalInstanceCounter;
  }

  public Map<Integer, Integer> getRecordBasedErrorCounter() {
    return recordBasedErrorCounter;
  }

  public Map<Integer, Set<String>> getErrorCollector() {
    return errorCollector;
  }

  public Map<ValidationErrorType, Integer> getTypeInstanceCounter() {
    return typeInstanceCounter;
  }

  public Map<ValidationErrorCategory, Integer> getCategoryInstanceCounter() {
    return categoryInstanceCounter;
  }
}
