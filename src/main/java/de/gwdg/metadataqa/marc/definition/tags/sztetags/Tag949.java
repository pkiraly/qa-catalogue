package de.gwdg.metadataqa.marc.definition.tags.sztetags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Locally defined field in SZTE
 */
public class Tag949 extends DataFieldDefinition {

  private static Tag949 uniqueInstance;

  private Tag949() {
    initialize();
    postCreation();
  }

  public static Tag949 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag949();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "949";
    label = "Item";
    mqTag = "Item";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Aktuális jelzet", "NR",
      "b", "Call number", "NR",
      "c", "Példányazonosító", "NR",
      "d", "Tematikus jelzet", "NR",
      "e", "Törlési szám", "NR",
      "f", "Ellenőrizve", "NR",
      "g", "Teremkód", "NR",
      "h", "Current location", "NR",
      "i", "Szériaazon. (periodika)", "NR",
      "j", "Egyéb tematikus jelzet", "NR",
      "k", "Kocsiszám", "NR",
      "l", "Permanent location", "NR",
      "m", "Beszerzés forrása", "NR",
      "n", "Inventory number", "NR",
      "o", "Példányazonosító", "NR",
      "p", "Periodikák száma", "NR",
      "q", "Compensation cost", "NR",
      "r", "Prime cost", "NR",
      "s", "Loan type", "NR",
      "t", "Item type", "NR",
      "u", "RFID", "NR",
      "v", "Volume", "NR",
      "w", "Former location", "NR",
      "x", "Loan-package", "NR",
      "y", "Issue date", "NR",
      "z", "Barcode", "NR",
      "B", "Blocks", "NR",
      "0", "ODR-type", "NR",
      "1", "Undefined", "NR",
      "2", "Undefined", "NR",
      "3", "Undefined", "NR",
      "4", "Undefined", "NR",
      "5", "Undefined", "NR",
      "6", "Undefined", "NR",
      "7", "Undefined", "NR",
      "8", "Undefined", "NR",
      "9", "Undefined", "NR"
    );
  }
}
