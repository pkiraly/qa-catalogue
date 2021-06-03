package de.gwdg.metadataqa.marc.model.validation;

import java.util.Arrays;
import java.util.List;

public enum ValidationErrorFormat {

  TAB_SEPARATED("tab separated", "tsv", "tab-separated"),
  COMMA_SEPARATED("comma separated", "csv", "comma-separated"),
  TEXT("simple text", "text", "txt"),
  JSON("JSON", "json")
  ;

  private List<String> names;
  private String label;

  ValidationErrorFormat(String label, String... names) {
    this.label = label;
    this.names = Arrays.asList(names);
  }

  public static ValidationErrorFormat byFormat(String format) {
    for (ValidationErrorFormat registeredFormat : ValidationErrorFormat.values()) {
      if (registeredFormat.getNames().contains(format)) {
        return registeredFormat;
      }
    }
    return null;
  }

  public String getName() {
    return names.get(0);
  }

  public List<String> getNames() {
    return names;
  }

  public String getLabel() {
    return label;
  }
}
