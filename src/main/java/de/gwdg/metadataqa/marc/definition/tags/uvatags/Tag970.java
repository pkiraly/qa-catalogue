package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Printer Added Entry (Local)
 */
public class Tag970 extends DataFieldDefinition {
  private static Tag970 uniqueInstance;

  private Tag970() {
    initialize();
    postCreation();
  }

  public static Tag970 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag970();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "970";
    label = "Printer Added Entry (Local)";
    mqTag = "PrinterAddedEntryLocal";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Place of publication/manufacture", "NR",
      "b", "Years of publication/manufacture", "NR",
      "n", "Name of publisher/manufacturer", "NR",
      "g", "Addition to name / miscellaneous information", "NR",
      "0", "Linking PPN STCN", "NR"
    );

    getSubfield("a").setMqTag("PlaceOfPublicationOrManufacture");
    getSubfield("b").setMqTag("YearsOfPublicationOrManufacture");
    getSubfield("n").setMqTag("NameOfPublisherOrManufacturer");
    getSubfield("g").setMqTag("AdditionToNameOrMiscellaneousInformation");
    getSubfield("0").setMqTag("LinkingPPNSTCN");
  }
}
