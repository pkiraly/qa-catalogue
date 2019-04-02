package de.gwdg.metadataqa.marc.definition.tags.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Address
 * http://www.loc.gov/marc/bibliographic/bd270.html
 */
public class Tag270 extends DataFieldDefinition {

  private static Tag270 uniqueInstance;

  private Tag270() {
    initialize();
    postCreation();
  }

  public static Tag270 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag270();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "270";
    label = "Address";
    mqTag = "Address";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd270.html";

    ind1 = new Indicator("Level")
      .setCodes(
        " ", "No level specified",
        "1", "Primary",
        "2", "Secondary"
      )
      .setMqTag("level");
    ind2 = new Indicator("Type of address")
      .setCodes(
        " ", "No type specified",
        "0", "Mailing",
        "7", "Type specified in subfield $i"
      )
      .setMqTag("type")
      .setFrbrFunctions(ManagementIdentify);

    setSubfieldsWithCardinality(
      "a", "Address", "R",
      "b", "City", "NR",
      "c", "State or province", "NR",
      "d", "Country", "NR",
      "e", "Postal code", "NR",
      "f", "Terms preceding attention name", "NR",
      "g", "Attention name", "NR",
      "h", "Attention position", "NR",
      "i", "Type of address", "NR",
      "j", "Specialized telephone number", "R",
      "k", "Telephone number", "R",
      "l", "Fax number", "R",
      "m", "Electronic mail address", "R",
      "n", "TDD or TTY number", "R",
      "p", "Contact person", "R",
      "q", "Title of contact person", "R",
      "r", "Hours", "R",
      "z", "Public note", "R",
      "4", "Relationship", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("4").setCodeList(RelatorCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoveryObtain, ManagementDisplay);
    getSubfield("b").setMqTag("city")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("c").setMqTag("state")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("d").setMqTag("country")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("e").setMqTag("postalCode")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("f").setMqTag("precedingTerms")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("g").setMqTag("attentionName")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("h").setMqTag("attentionPosition")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("i").setMqTag("typeOfAddress")
      .setFrbrFunctions(ManagementDisplay);
    getSubfield("j").setMqTag("specializedPhone")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("k").setMqTag("phone")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("l").setMqTag("fax")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("m").setMqTag("email")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("n").setMqTag("tddOrTty")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("p").setMqTag("contactPerson")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("q").setMqTag("contactPersonTitle")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("r").setMqTag("hours")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("z").setMqTag("note")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("4").setMqTag("relationship");
    getSubfield("6").setMqTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
