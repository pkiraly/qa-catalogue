package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseManage;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseOperate;

/**
 * Form of item
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006mixed06 extends ControlfieldPositionDefinition {
  private static Tag006mixed06 uniqueInstance;

  private Tag006mixed06() {
    initialize();
    extractValidCodes();
  }

  public static Tag006mixed06 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006mixed06();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Form of item";
    id = "006mixed06";
    mqTag = "formOfItem";
    positionStart = 6;
    positionEnd = 7;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      " ", "None of the following",
      "a", "Microfilm",
      "b", "Microfiche",
      "c", "Microopaque",
      "d", "Large print",
      "f", "Braille",
      "o", "Online",
      "q", "Direct electronic",
      "r", "Regular print reproduction",
      "s", "Electronic",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage, UseOperate);
  }
}