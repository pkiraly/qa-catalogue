package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.Utils;

import java.util.*;

public class ThompsonTraillScores {
  private Map<ThompsonTraillFields, Integer> scores;

  public ThompsonTraillScores() {
    scores = new LinkedHashMap<>();
    for (ThompsonTraillFields field : ThompsonTraillFields.values()) {
      if (!field.equals(ThompsonTraillFields.ID)) {
        scores.put(field, 0);
      }
    }
  }

  public void count(ThompsonTraillFields key) {
    Utils.count(key, scores);
  }

  /**
   * Get the value of a key. If key is not found it returns null.
   * @param key
   */
  public Integer get(ThompsonTraillFields key) {
    return scores.getOrDefault(key, null);
  }

  public void set(ThompsonTraillFields key, int value) {
    scores.put(key, value);
  }

  public void calculateTotal() {
    var total = 0;
    for (Map.Entry<ThompsonTraillFields, Integer> entry : scores.entrySet()) {
      ThompsonTraillFields field = entry.getKey();
      if (!field.equals(ThompsonTraillFields.TOTAL)) {
        if (field.isClassification())
          total += Math.min(entry.getValue(), 10);
        else
          total += entry.getValue();
      }
    }
    set(ThompsonTraillFields.TOTAL, total);
  }

  public List<Integer> asList() {
    List<Integer> list = new ArrayList<>();
    for (Map.Entry<ThompsonTraillFields, Integer> entry : scores.entrySet()) {
      ThompsonTraillFields field = entry.getKey();
      if (field.isClassification())
        list.add(Math.min(entry.getValue(), 10));
      else
        list.add(entry.getValue());
    }
    return list;
  }
}
