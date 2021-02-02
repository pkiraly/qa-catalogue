package de.gwdg.metadataqa.marc.definition;

import java.util.Arrays;
import java.util.List;

public enum SourceSpecificationType {
  Indicator1Is7AndSubfield2("7"),
  Indicator1IsSpaceAndSubfield2(" "),
  Indicator2AndSubfield2("7"),
  Indicator2For055AndSubfield2("6", "7", "8", "9"),
  Subfield2(""),
  Indicator2("");

  private List<String> indicators;

  SourceSpecificationType(String... indicators) {
    this.indicators = Arrays.asList(indicators);
  }

  public List<String> getIndicators() {
    return indicators;
  }
}
