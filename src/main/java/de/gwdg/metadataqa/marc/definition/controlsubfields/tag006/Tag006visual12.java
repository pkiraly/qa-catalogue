package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Form of item
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006visual12 extends ControlSubfieldDefinition {
  private static Tag006visual12 uniqueInstance;

  private Tag006visual12() {
    initialize();
    extractValidCodes();
  }

  public static Tag006visual12 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006visual12();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Form of item";
    id = "tag006visual12";
    mqTag = "formOfItem";
    positionStart = 12;
    positionEnd = 13;
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