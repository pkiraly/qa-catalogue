package de.gwdg.metadataqa.marc.definition.tags.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementDisplay;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Address
 * https://www.loc.gov/marc/bibliographic/bd270.html
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
    setCompilanceLevels("O");

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

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(DiscoveryObtain, ManagementDisplay)
      .setCompilanceLevels("A");

    getSubfield("b")
      .setMqTag("city")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("state")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("d")
      .setMqTag("country")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("e")
      .setMqTag("postalCode")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("f")
      .setMqTag("precedingTerms")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("g")
      .setMqTag("attentionName")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("h")
      .setMqTag("attentionPosition")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("i")
      .setMqTag("typeOfAddress")
      .setFrbrFunctions(ManagementDisplay)
      .setCompilanceLevels("A");

    getSubfield("j")
      .setMqTag("specializedPhone")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("k")
      .setMqTag("phone")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("l")
      .setMqTag("fax")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("m")
      .setMqTag("email")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("n")
      .setMqTag("tddOrTty")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("p")
      .setMqTag("contactPerson")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("q")
      .setMqTag("contactPersonTitle")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("r")
      .setMqTag("hours")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("z")
      .setMqTag("note")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("4")
      .setMqTag("relationship")
      .setCodeList(RelatorCodes.getInstance())
      .setCompilanceLevels("O");

    getSubfield("6")
      .setMqTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
