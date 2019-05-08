package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Organization and Arrangement of Materials
 * http://www.loc.gov/marc/bibliographic/bd351.html
 */
public class Tag351 extends DataFieldDefinition {
  private static Tag351 uniqueInstance;

  private Tag351() {
    initialize();
    postCreation();
  }

  public static Tag351 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag351();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "351";
    label = "Organization and Arrangement of Materials";
    bibframeTag = "Arrangement";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd351.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Organization", "R",
      "b", "Arrangement", "R",
      "c", "Hierarchical level", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("organization")
      .setFrbrFunctions(UseInterpret);
    getSubfield("b").setBibframeTag("pattern")
      .setFrbrFunctions(UseInterpret);
    getSubfield("c").setBibframeTag("hierarchicalLevel")
      .setFrbrFunctions(UseInterpret);
    getSubfield("3").setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
