package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.Utils;

import java.util.*;

public class ThompsonTrailScores {
  private List<String> classifications = Arrays.asList(
    "classification-lc-nlm", "classification-loc", "classification-mesh",
    "classification-fast", "classification-gnd", "classification-other"
  );

  private Map<String, Integer> scores;

  public ThompsonTrailScores(Map<String, String> fields) {
    scores = new LinkedHashMap<>();
    for (String key : fields.values()) {
      if (!key.equals("id")) {
        scores.put(key, 0);
      }
    }
  }

  public void count(String key) {
    Utils.count(key, scores);
  }

  public void set(String key, int value) {
    scores.put(key, value);
  }

  public void calculateTotal() {
    int total = 0;
    for (Map.Entry<String, Integer> entry : scores.entrySet()) {
      if (!entry.getKey().equals("total")) {
        if (classifications.contains(entry.getKey()))
          total += Math.min(entry.getValue(), 10);
        else
          total += entry.getValue();
      }
    }
    set("total", total);
  }

  public List<Integer> asList() {
    List<Integer> list = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : scores.entrySet()) {
      if (classifications.contains(entry.getKey()))
        list.add(Math.min(entry.getValue(), 10));
      else
        list.add(entry.getValue());
    }
    return list;
  }
}
