package de.gwdg.metadataqa.marc.analysis.contextual.authority;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AuthorityCategory {
  PERSONAL("Personal names", 1),
  CORPORATE("Corporate names", 2),
  MEETING("Meeting names", 3),
  GEOGRAPHIC("Geographic names", 4),
  TITLES("Titles", 5),
  OTHER("Other", 6)
  ;

  private final int id;
  private final String label;
  private static Map<String, AuthorityCategory> index = new HashMap<>();

  AuthorityCategory(String label, int id) {
    this.label = label;
    this.id = id;
  }

  public String getLabel() {
    return label;
  }

  public int getId() {
    return id;
  }

  @Override
  public String toString() {
    return "AuthorityCategory{" +
      "label='" + label + '\'' +
      '}';
  }
}
