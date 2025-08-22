package de.gwdg.metadataqa.marc.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Counter<T> {
  private final Map<T, Integer> counterMap = new HashMap<>();

  public void count(T key) {
    add(key, 1);
  }

  public void add(T key, int i) {
    counterMap.computeIfAbsent(key, s -> 0);
    counterMap.put(key, counterMap.get(key) + i);
  }

  public int get(T key) {
    return counterMap.getOrDefault(key, null);
  }

  public Set<T> keys() {
    return counterMap.keySet();
  }

  public Set<Map.Entry<T, Integer>> entrySet() {
    return counterMap.entrySet();
  }

  public Map<T, Integer> getMap() {
    return counterMap;
  }

  public int total() {
    var total = 0;
    for (int value : counterMap.values())
      total += value;
    return total;
  }
}
