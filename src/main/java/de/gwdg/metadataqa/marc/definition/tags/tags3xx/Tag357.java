package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Originator Dissemination Control
 * http://www.loc.gov/marc/bibliographic/bd357.html
 */
public class Tag357 extends DataFieldDefinition {
  private static Tag357 uniqueInstance;

  private Tag357() {
    initialize();
    postCreation();
  }

  public static Tag357 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag357();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "357";
    label = "Originator Dissemination Control";
    mqTag = "OriginatorDisseminationControl";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd357.html";
    setCompilanceLevels("A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Originator control term", "NR",
      "b", "Originating agency", "R",
      "c", "Authorized recipients of material", "R",
      "g", "Other restrictions", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(UseRestrict)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setMqTag("originatingAgency")
      .setFrbrFunctions(UseRestrict)
      .setCompilanceLevels("M");

    getSubfield("c")
      .setMqTag("authorizedRecipients")
      .setFrbrFunctions(UseRestrict)
      .setCompilanceLevels("A");

    getSubfield("g")
      .setMqTag("otherRestrictions")
      .setFrbrFunctions(UseRestrict)
      .setCompilanceLevels("A");

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
