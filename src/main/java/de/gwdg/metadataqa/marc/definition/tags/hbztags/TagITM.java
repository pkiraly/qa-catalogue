package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Items Information (ITM) from ALMA Publishing
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658
 */
public class TagITM extends DataFieldDefinition {

  private static TagITM uniqueInstance;

  private TagITM() {
    initialize();
    postCreation();
  }

  public static TagITM getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagITM();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "ITM";
    label = "Items Information (ITM)";
    mqTag = "ItemsInformation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality("a", "Item PID subfield", "R",
      "b", "Barcode subfield", "R",
      "e", "Copy ID subfield", "R",
      "f", "Material type subfield", "R",
      "p", "Item policy subfield", "R",
      "s", "Item status subfield", "R",
      "g", "Provenance subfield", "R",
      "h", "Is magnetic subfield", "R",
      "i", "Enumeration A subfield", "R",
      "j", "Enumeration B subfield", "R",
      "k", "Chronology I subfield", "R",
      "m", "Chronology J subfield", "R",
      "q", "Description subfield", "R",
      "r", "Process type subfield", "R",
      "u", "Permanent library subfield", "R",
      "v", "Permanent location subfield", "R",
      "w", "Current library subfield", "R",
      "x", "Current location subfield", "R",
      "d", "Call number type subfield", "R",
      "c", "Call number subfield", "R",
      "t", "Item call number type subfield", "R",
      "n", "Item call number subfield", "R",
      "y", "Temporary call number type subfield", "R",
      "z", "Temporary call number subfield", "R",
      "B", "Inventory number subfield", "R",
      "D", "Inventory date subfield", "R",
      "E", "Storage location ID subfield", "R",
      "F", "Pages subfield", "R",
      "G", "Pieces subfield", "R",
      "J", "Public note subfield", "R",
      "K", "Fulfillment note subfield", "R",
      "L", "Internal note 1 subfield", "R",
      "N", "Internal note 2 subfield", "R",
      "O", "Internal note 3 subfield", "R",
      "P", "Statistics note 1 subfield", "R",
      "Q", "Statistics note 2 subfield", "R",
      "R", "Statistics note 3 subfield", "R",
      "S", "PO line number subfield", "R",
      "T", "Due back date subfield", "R",
      "U", "Receiving date subfield", "R",
      "V", "Created by subfield", "R",
      "W", "Create date subfield", "R",
      "X", "Updated by subfield", "R",
      "Y", "Update date subfield", "R",
      "H", "Holdings ID subfield", "R",
      "M", "Member code subfield", "R",
      "C", "Campus of current library subfield", "R",
      "Z", "Physical condition subfield", "R",
      "A", "Committed to retain subfield ", "R",
      "I", "Retention reason subfield", "R",
      "l", "Retention note subfield", "R"
  );
  }
}
