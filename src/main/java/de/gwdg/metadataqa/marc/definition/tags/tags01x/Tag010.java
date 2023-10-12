package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Library of Congress Control Number
 * http://www.loc.gov/marc/bibliographic/bd010.html
 */
public class Tag010 extends DataFieldDefinition {

  private static Tag010 uniqueInstance;

  private Tag010() {
    initialize();
    postCreation();
  }

  public static Tag010 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag010();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "010";
    label = "Library of Congress Control Number";
    bibframeTag = "IdentifiedBy/Lccn";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd010.html";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "LC control number", "NR",
      "b", "NUCMC control number", "R",
      "z", "Canceled/invalid LC control number", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("a")
      .setBibframeTag("rdf:value")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A", "A");

    getSubfield("b")
      .setMqTag("numcControlNumber")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A", "A");

    getSubfield("z")
      .setMqTag("canceled")
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
