package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Target audience
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006music05 extends ControlfieldPositionDefinition {
  private static Tag006music05 uniqueInstance;

  private Tag006music05() {
    initialize();
    extractValidCodes();
  }

  public static Tag006music05 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006music05();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Target audience";
    id = "tag006music05";
    mqTag = "targetAudience";
    positionStart = 5;
    positionEnd = 6;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      " ", "Unknown or unspecified",
      "a", "Preschool",
      "b", "Primary",
      "c", "Pre-adolescent",
      "d", "Adolescent",
      "e", "Adult",
      "f", "Specialized",
      "g", "General",
      "j", "Juvenile",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoverySelect);
  }
}