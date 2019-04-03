package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Creation/Production Credits Note
 * http://www.loc.gov/marc/bibliographic/bd508.html
 */
public class Tag508 extends DataFieldDefinition {

  private static Tag508 uniqueInstance;

  private Tag508() {
    initialize();
    postCreation();
  }

  public static Tag508 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag508();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "508";
    label = "Creation/Production Credits Note";
    mqTag = "Credits";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd508.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Creation/production credits note", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
