package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Film inspection date
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture17 extends ControlfieldPositionDefinition {
  private static Tag007motionPicture17 uniqueInstance;

  private Tag007motionPicture17() {
    initialize();
    extractValidCodes();
  }

  public static Tag007motionPicture17 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007motionPicture17();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Film inspection date";
    id = "007motionPicture17";
    mqTag = "filmInspectionDate";
    positionStart = 17;
    positionEnd = 23;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
    functions = Arrays.asList(UseManage);

    // TODO: check documentation
    // it should handle:
    // * ccyymm (century/year/month)
    // * ccyymm (century/year/month) with - in unknown parts
    // * |||||| for "No attempt to code"
  }
}