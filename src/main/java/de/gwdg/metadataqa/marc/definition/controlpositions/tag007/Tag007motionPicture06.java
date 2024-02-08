package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseManage;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseOperate;

/**
 * Medium for sound
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture06 extends ControlfieldPositionDefinition {
  private static Tag007motionPicture06 uniqueInstance;

  private Tag007motionPicture06() {
    initialize();

  }

  public static Tag007motionPicture06 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007motionPicture06();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Medium for sound";
    id = "007motionPicture06";
    mqTag = "mediumForSound";
    positionStart = 6;
    positionEnd = 7;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
    codes = Utils.generateCodes(
      " ", "No sound (silent)",
      "a", "Optical sound track on motion picture film",
      "b", "Magnetic sound track on motion picture film",
      "c", "Magnetic audio tape in cartridge",
      "d", "Sound disc",
      "e", "Magnetic audio tape on reel",
      "f", "Magnetic audio tape in cassette",
      "g", "Optical and magnetic sound track on motion picture film",
      "h", "Videotape",
      "i", "Videodisc",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage, UseOperate);
  }
}