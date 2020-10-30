package de.gwdg.metadataqa.marc.cli.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Collocation {
  String key;
  Integer value;

  public Collocation(List<String> key, Integer value) {
    this.key = StringUtils.join(key, ";");
    this.value = value;
  }

  public int compareTo(Collocation other) {
    int i = getKey().compareTo(other.getKey());
    if (i != 0) {
      i = getValue().compareTo(other.getValue());
    }
    return i;
  }

  public String getKey() {
    return key;
  }

  public Integer getValue() {
    return value;
  }
}
