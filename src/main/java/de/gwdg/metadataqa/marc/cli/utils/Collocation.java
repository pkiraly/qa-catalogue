package de.gwdg.metadataqa.marc.cli.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static de.gwdg.metadataqa.marc.Utils.createRow;

public class Collocation {
  String key;
  Integer count;
  double percent;

  public Collocation(List<String> key, Integer count, Integer total) {
    this.key = StringUtils.join(key, ";");
    this.count = count;
    this.percent = (double)count * 100 / total;
  }

  public static String header() {
    return createRow("abbreviations", "recordcount", "percent");
  }

  public String formatRow() {
    return createRow(key, count, String.format("%.2f%%", percent));
  }

  public int compareTo(Collocation other) {
    int i = getCount().compareTo(other.getCount());
    if (i == 0) {
      i = getKey().compareTo(other.getKey());
    }
    return i;
  }

  public String getKey() {
    return key;
  }

  public Integer getCount() {
    return count;
  }
}
