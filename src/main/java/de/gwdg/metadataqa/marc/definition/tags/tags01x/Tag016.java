package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * National Bibliographic Agency Control Number
 * http://www.loc.gov/marc/bibliographic/bd016.html
 */
public class Tag016 extends DataFieldDefinition {

  private static Tag016 uniqueInstance;

  private Tag016() {
    initialize();
    postCreation();
  }

  public static Tag016 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag016();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "016";
    label = "National Bibliographic Agency Control Number";
    bibframeTag = "IdIntifiedBy/Local";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd016.html";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator("National bibliographic agency")
      .setCodes(
        " ", "Library and Archives Canada",
        "7", "Source specified in subfield $2"
      )
      .setMqTag("agency")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Record control number", "NR",
      "z", "Canceled/invalid control number", "R",
      "2", "Source", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(OrganizationCodes.getInstance());

    getSubfield("a")
      .setBibframeTag("rdf:value")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A", "A");

    getSubfield("z")
      .setMqTag("canceledOrInvalidControlNumber")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A", "A");

    getSubfield("2")
      .setBibframeTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A", "A");

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
