package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Hours, etc.
 * http://www.loc.gov/marc/bibliographic/bd307.html
 */
public class Tag307 extends DataFieldDefinition {
  private static Tag307 uniqueInstance;

  private Tag307() {
    initialize();
    postCreation();
  }

  public static Tag307 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag307();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "307";
    label = "Hours, etc.";
    mqTag = "Hours";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd307.html";
    setLevels("O");

    ind1 = new Indicator("Display constant controller")
      .setCodes(
        " ", "Hours",
        "8", "No display constant generated"
      )
      .setMqTag("displayConstant")
      .setFrbrFunctions(ManagementDisplay);

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Hours", "NR",
      "b", "Additional information", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    // TODO: $a is complex := <days of the week>, <hours>[;]
    // ';' if there is $b
    // <days of the week> :=
    // Sunday   Su
    // Monday   M
    // Tuesday   Tu
    // Wednesday   W
    // Thursday   Th
    // Friday   F
    // Saturday   Sa

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySelect, DiscoveryObtain)
      .setLevels("M");

    getSubfield("b")
      .setMqTag("additionalInformation")
      .setLevels("O");

    getSubfield("6")
      .setMqTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("O");
  }
}
