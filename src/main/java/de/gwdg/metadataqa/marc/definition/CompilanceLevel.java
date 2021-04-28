package de.gwdg.metadataqa.marc.definition;

import java.util.HashMap;
import java.util.Map;

public enum CompilanceLevel {
  MandatoryIfApplicable("Mandatory if applicable", "A"),
  Mandatory("Mandatory", "M"),
  Optional("Optional", "O"),
  ;

  private String label = null;
  private String abbreviation = null;
  static Map<String, CompilanceLevel> abbreviations = new HashMap<>();

  CompilanceLevel(String label, String abbreviation) {
    this.label = label;
    this.abbreviation = abbreviation;
  }

  private static void index() {
    for (CompilanceLevel level : values())
      abbreviations.put(level.abbreviation, level);
  }

  public static CompilanceLevel byAbbreviation(String abbreviation) {
    if (abbreviations.isEmpty())
      index();
    if (!abbreviations.containsKey(abbreviation))
      throw new IllegalArgumentException("inexistent abbreviation: " + abbreviation);
    return abbreviations.getOrDefault(abbreviation, null);
  }

  public String getLabel() {
    return label;
  }

  public String getAbbreviation() {
    return abbreviation;
  }
}
