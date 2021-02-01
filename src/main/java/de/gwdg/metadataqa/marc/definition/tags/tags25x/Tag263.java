package de.gwdg.metadataqa.marc.definition.tags.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Projected Publication Date
 * http://www.loc.gov/marc/bibliographic/bd263.html
 */
public class Tag263 extends DataFieldDefinition {

  private static Tag263 uniqueInstance;

  private Tag263() {
    initialize();
    postCreation();
  }

  public static Tag263 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag263();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "263";
    label = "Projected Publication Date";
    mqTag = "ProjectedPublicationDate";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd263.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Projected publication date", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    // TODO $a - regex: yyyymm and '-' for the unknown portion of the date.

    getSubfield("a")
      .setBibframeTag("projectedProvisionDate")
      .setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("M");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");
  }
}
