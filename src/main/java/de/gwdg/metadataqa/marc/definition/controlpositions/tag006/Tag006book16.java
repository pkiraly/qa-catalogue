package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Literary form
 * same as 008/33
 * https://www.loc.gov/marc/bibliographic/bd006.html
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag006book16 extends ControlfieldPositionDefinition {
  private static Tag006book16 uniqueInstance;

  private Tag006book16() {
    initialize();
    extractValidCodes();
  }

  public static Tag006book16 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006book16();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Literary form";
    id = "006book16";
    mqTag = "literaryForm";
    positionStart = 16;
    positionEnd = 17;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      "0", "Not fiction (not further specified)",
      "1", "Fiction (not further specified)",
      "d", "Dramas",
      "e", "Essays",
      "f", "Novels",
      "h", "Humor, satires, etc.",
      "i", "Letters",
      "j", "Short stories",
      "m", "Mixed forms",
      "p", "Poetry",
      "s", "Speeches",
      "u", "Unknown",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect);
  }
}