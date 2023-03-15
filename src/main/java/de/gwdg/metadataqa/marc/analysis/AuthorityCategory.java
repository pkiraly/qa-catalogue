package de.gwdg.metadataqa.marc.analysis;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AuthorityCategory {
  PERSONAL("Personal names", 1, "100", "700", "800"),
  CORPORATE("Corporate names", 2, "110", "710", "810"),
  MEETING("Meeting names", 3,"111", "711", "811"),
  GEOGRAPHIC("Geographic names", 4, "751", "752"),
  TITLES("Titles", 5, "130", "730", "740", "830"),
  OTHER("Other", 6, "720", "753", "754")
  ;

  private final int id;
  private final String label;
  private final List<String> tags;
  private static Map<String, AuthorityCategory> index = new HashMap<>();

  AuthorityCategory(String label, int id, String... tags) {
    this.label = label;
    this.id = id;
    this.tags = Arrays.asList(tags);
  }

  public String getLabel() {
    return label;
  }

  public int getId() {
    return id;
  }

  public List<String> getTags() {
    return tags;
  }

  public static AuthorityCategory get(String tag) {
    if (index.isEmpty())
      for (AuthorityCategory category : values())
        for (String t : category.tags)
          index.put(t, category);

    return index.getOrDefault(tag, null);
  }

  @Override
  public String toString() {
    return "AuthorityCategory{" +
      "label='" + label + '\'' +
      ", tags=" + StringUtils.join(tags) +
      '}';
  }
}
