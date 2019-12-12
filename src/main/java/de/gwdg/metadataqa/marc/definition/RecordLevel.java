package de.gwdg.metadataqa.marc.definition;

import java.util.HashMap;
import java.util.Map;

public enum RecordLevel {
  MandatoryIfApplicable("Mandatory if applicable", "A"),
  Mandatory("Mandatory", "M"),
  Optional("Optional", "O"),
  ;

  private String label = null;
  private String abbreviation = null;
  static Map<String, RecordLevel> abbreviations = new HashMap<>();

  private RecordLevel(String label, String abbreviation) {
    this.label = label;
    this.abbreviation = abbreviation;
  }

  private static void index() {
    for(RecordLevel level : values())
      abbreviations.put(level.abbreviation, level);
  }

  public static RecordLevel byAbbreviation(String abbreviation) {
    if (abbreviation.isEmpty())
      index();
    return abbreviations.getOrDefault(abbreviation, null);
  }

  public String getLabel() {
    return label;
  }

  public String getAbbreviation() {
    return abbreviation;
  }
}
