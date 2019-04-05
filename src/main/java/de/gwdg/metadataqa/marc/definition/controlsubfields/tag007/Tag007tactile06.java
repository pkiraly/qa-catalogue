package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Braille music format
 * https://www.loc.gov/marc/bibliographic/bd007f.html
 */
public class Tag007tactile06 extends ControlSubfieldDefinition {
  private static Tag007tactile06 uniqueInstance;

  private Tag007tactile06() {
    initialize();
    extractValidCodes();
  }

  public static Tag007tactile06 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007tactile06();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Braille music format";
    id = "tag007tactile06";
    mqTag = "brailleMusicFormat";
    positionStart = 6;
    positionEnd = 9;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007f.html";
    codes = Utils.generateCodes(
      " ", "No specified braille music format",
      "a", "Bar over bar",
      "b", "Bar by bar",
      "c", "Line over line",
      "d", "Paragraph",
      "e", "Single line",
      "f", "Section by section",
      "g", "Line by line",
      "h", "Open score",
      "i", "Spanner short form scoring",
      "j", "Short form scoring",
      "k", "Outline",
      "l", "Vertical score",
      "n", "Not applicable",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    repeatableContent = true;
    unitLength = 1;
    functions = Arrays.asList(DiscoverySelect);
  }
}