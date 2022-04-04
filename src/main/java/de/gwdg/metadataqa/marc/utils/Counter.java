package de.gwdg.metadataqa.marc.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Counter<T> {
  private Map<T, Integer> counter = new HashMap<>();

  public void count(T key) {
    add(key, 1);
  }

  public void add(T key, int i) {
    counter.computeIfAbsent(key, s -> 0);
    counter.put(key, counter.get(key) + i);
  }

  public int get(T key) {
    return counter.getOrDefault(key, null);
  }

  public Set<T> keys() {
    return counter.keySet();
  }

  public Set<Map.Entry<T, Integer>> entrySet() {
    return counter.entrySet();
  }

  public Map<T, Integer> getMap() {
    return counter;
  }

  public int total() {
    var total = 0;
    for (int value : counter.values())
      total += value;
    return total;
  }
}
