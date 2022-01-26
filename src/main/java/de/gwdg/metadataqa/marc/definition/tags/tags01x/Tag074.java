package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * GPO Item Number
 * http://www.loc.gov/marc/bibliographic/bd074.html
 * GPO = U.S. Government Printing Office
 */
public class Tag074 extends DataFieldDefinition {

  private static Tag074 uniqueInstance;

  private Tag074() {
    initialize();
    postCreation();
  }

  public static Tag074 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag074();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "074";
    label = "GPO Item Number";
    mqTag = "GPOItemNumber";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd074.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "GPO item number", "NR",
      "z", "Canceled/invalid GPO item number", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("a")
      .setBibframeTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("M");

    getSubfield("z")
      .setMqTag("canceled")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR"),
      new SubfieldDefinition("@", "Language of field", "NR"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR")
    ));
  }
}
