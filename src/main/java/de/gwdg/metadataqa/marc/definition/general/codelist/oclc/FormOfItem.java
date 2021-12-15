package de.gwdg.metadataqa.marc.definition.general.codelist.oclc;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.general.codelist.CodeList;

/**
 * Form: Form of Item
 * http://www.oclc.org/bibformats/en/fixedfield/form.html
 */
public class FormOfItem extends CodeList {

  private void initialize() {
    name = "Form of Item";
    url = "https://www.oclc.org/bibformats/en/fixedfield/form.html";
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
      "o", "Online",
      "q", "Direct electronic"
    );
    indexCodes();
  }

  private static FormOfItem uniqueInstance;

  private FormOfItem() {
    initialize();
  }

  public static FormOfItem getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new FormOfItem();
    return uniqueInstance;
  }
}