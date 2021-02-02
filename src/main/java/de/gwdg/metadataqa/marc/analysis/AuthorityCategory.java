package de.gwdg.metadataqa.marc.analysis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AuthorityCategory {
  Personal("Personal names", "100", "700", "800"),
  Corporate("Corporate names", "110", "710", "810"),
  Meeting("Meeting names", "111", "711", "811"),
  Geographic("Geographic names", "751", "752"),
  Titles("Titles", "130", "730", "740", "830"),
  Other("Other", "720", "753", "754")
  ;

  private String label;
  private List<String> tags;
  private static Map<String, AuthorityCategory> index = new HashMap<>();

  AuthorityCategory(String label, String... tags) {
    this.label = label;
    this.tags = Arrays.asList(tags);
  }

  public String getLabel() {
    return label;
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
}
