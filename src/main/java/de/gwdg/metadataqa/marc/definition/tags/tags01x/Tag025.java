package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Overseas Acquisition Number
 * http://www.loc.gov/marc/bibliographic/bd025.html
 */
public class Tag025 extends DataFieldDefinition {

  private static Tag025 uniqueInstance;

  private Tag025() {
    initialize();
    postCreation();
  }

  public static Tag025 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag025();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "025";
    label = "Overseas Acquisition Number";
    bibframeTag = "LcOverseasAcq";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd025.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Overseas acquisition number", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("a").setBibframeTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, UsageManage);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
