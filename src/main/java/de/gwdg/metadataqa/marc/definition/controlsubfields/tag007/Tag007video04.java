package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Videorecording format
 * https://www.loc.gov/marc/bibliographic/bd007v.html
 */
public class Tag007video04 extends ControlSubfieldDefinition {
  private static Tag007video04 uniqueInstance;

  private Tag007video04() {
    initialize();
    extractValidCodes();
  }

  public static Tag007video04 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007video04();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Videorecording format";
    id = "tag007video04";
    mqTag = "videorecordingFormat";
    positionStart = 4;
    positionEnd = 5;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007v.html";
    codes = Utils.generateCodes(
      "a", "Beta (1/2 in., videocassette)",
      "b", "VHS (1/2 in., videocassette)",
      "c", "U-matic (3/4 in., videocasstte)",
      "d", "EIAJ (1/2 in., reel)",
      "e", "Type C (1 in., reel)",
      "f", "Quadruplex (1 in. or 2 in., reel)",
      "g", "Laserdisc",
      "h", "CED (Capacitance Electronic Disc) videodisc",
      "i", "Betacam (1/2 in., videocassette)",
      "j", "Betacam SP (1/2 in., videocassette)",
      "k", "Super-VHS (1/2 in., videocassette)",
      "m", "M-II (1/2 in., videocassette)",
      "o", "D-2 (3/4 in., videocassette)",
      "p", "8 mm.",
      "q", "Hi-8 mm.",
      "s", "Blu-ray disc",
      "u", "Unknown",
      "v", "DVD",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UsageOperate);
    historicalCodes = Utils.generateCodes(
      " ", "Not applicable or no attempt to code [OBSOLETE, 1980]",
      "n", "Not applicable [OBSOLETE, 1981]"
    );
  }
}