package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Physical Location
 * http://www.oclc.org/bibformats/en/8xx/851.html
 */
public class Tag851 extends DataFieldDefinition {

  private static Tag851 uniqueInstance;

  private Tag851() {
    initialize();
    postCreation();
  }

  public static Tag851 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag851();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "851";
    label = "Physical Location";
    mqTag = "PhysicalLocation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "http://www.oclc.org/bibformats/en/8xx/851.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Name, custodian, or owner", "NR",
      "b", "Institutional division", "NR",
      "c", "Street address", "NR",
      "d", "Country", "NR",
      "e", "Location of units", "NR",
      "f", "Item number", "NR",
      "g", "Repository location code", "NR",
      "3", "Materials specified", "NR"
    );

    getSubfield("a").setMqTag("nameCustodianOrOwner");
    getSubfield("b").setMqTag("institutionalDivision");
    getSubfield("c").setMqTag("street");
    getSubfield("d").setMqTag("country");
    getSubfield("e").setMqTag("locationOfUnits");
    getSubfield("f").setMqTag("itemNumber");
    getSubfield("g").setMqTag("repositoryLocationCode");
    getSubfield("3").setMqTag("materialsSpecified");
  }
}
