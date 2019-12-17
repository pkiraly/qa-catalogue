package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Participant or Performer Note
 * http://www.loc.gov/marc/bibliographic/bd511.html
 */
public class Tag511 extends DataFieldDefinition {

  private static Tag511 uniqueInstance;

  private Tag511() {
    initialize();
    postCreation();
  }

  public static Tag511 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag511();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "511";
    label = "Participant or Performer Note";
    bibframeTag = "Credits";
    mqTag = "ParticipantOrPerformer";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd511.html";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator("Display constant controller")
      .setCodes(
        "0", "No display constant generated",
        "1", "Cast"
      )
      .setHistoricalCodes(
        "2", "Presenter (VM, MU) [OBSOLETE, 1993]",
        "3", "Narrator (VM, MU) [OBSOLETE, 1993]"
      )
      .setMqTag("displayConstant")
      .setFrbrFunctions(ManagementDisplay);

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Participant or performer note", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect)
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
