package de.gwdg.metadataqa.marc.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AppendableHashMap<K, V> {

  private Map<K, List<V>> map = new HashMap<>();

  public void append(K key, V value) {
    if (!map.containsKey(key))
      map.put(key, new ArrayList<>());
    map.get(key).add(value);
  }

  public Map<K, List<V>> getMap() {
    return map;
  }

  public int size() {
    return map.size();
  }

  public boolean isEmpty() {
    return map.isEmpty();
  }

  public boolean containsKey(K o) {
    return map.containsKey(o);
  }

  public boolean containsValue(List<V> o) {
    return map.containsValue(o);
  }

  public List<V> get(K o) {
    return map.get(o);
  }

  public Object put(K key, List<V> value) {
    return map.put(key, value);
  }

  public Object remove(K key) {
    return map.remove(key);
  }

  public void putAll(Map map) {
    this.map.putAll(map);
  }

  public void clear() {
    map.clear();
  }

  public Set keySet() {
    return map.keySet();
  }

  public Collection values() {
    return map.values();
  }

  public Set<Map.Entry<K, List<V>>> entrySet() {
    return map.entrySet();
  }
}
