package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Accessibility Content Source Codes
 * https://www.loc.gov/standards/sourcelist/accessibility.html
 */
public class AccessibilityContentSourceCodes extends CodeList {

  private void initialize() {
    name = "Accessibility Content Source Codes";
    url = "https://www.loc.gov/standards/sourcelist/accessibility.html";
    codes = Utils.generateCodes(
      "sapdv", "Schema.org Accessibility Properties for Discoverability Vocabulary (Accessibility Discoverability Vocabulary for Schema.org Community Group)"
    );
    indexCodes();
  }

  private static AccessibilityContentSourceCodes uniqueInstance;

  private AccessibilityContentSourceCodes() {
    initialize();
  }

  public static AccessibilityContentSourceCodes getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new AccessibilityContentSourceCodes();
    return uniqueInstance;
  }
}
