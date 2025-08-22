package de.gwdg.metadataqa.marc.analysis.serial;

import de.gwdg.metadataqa.marc.Utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SerialScores {
  private Map<SerialFields, Integer> scores;

  public SerialScores() {
    scores = new LinkedHashMap<>();
    for (SerialFields field : SerialFields.values()) {
      if (!field.equals(SerialFields.ID)) {
        scores.put(field, 0);
      }
    }
  }

  public void count(SerialFields key) {
    Utils.count(key, scores);
  }

  public void set(SerialFields key, int value) {
    scores.put(key, value);
  }

  public int get(SerialFields key) {
    return scores.getOrDefault(key, null);
  }

  public void calculateTotal() {
    var total = 0;
    for (Map.Entry<SerialFields, Integer> entry : scores.entrySet()) {
      var field = entry.getKey();
      if (field.equals(SerialFields.TOTAL)) {
        continue;
      }

      if (field.isClassification()) {
        total += Math.min(entry.getValue(), 10);
      } else {
        total += entry.getValue();
      }
    }
    set(SerialFields.TOTAL, total);
  }

  public List<Integer> asList() {
    List<Integer> list = new ArrayList<>();
    for (Map.Entry<SerialFields, Integer> entry : scores.entrySet()) {
      SerialFields field = entry.getKey();
      if (field.isClassification()) {
        list.add(Math.min(entry.getValue(), 10));
      }
      else {
        list.add(entry.getValue());
      }
    }
    return list;
  }

  /**
   * Reset all scores to 0. Used to discard all previous scores in case all other scores become irrelevant.
   */
  public void resetAll() {
    for (SerialFields field : SerialFields.values()) {
      if (!field.equals(SerialFields.ID)) {
        scores.put(field, 0);
      }
    }
  }

  public Map<SerialFields, Integer> getScores() {
    return scores;
  }
}
