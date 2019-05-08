package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Accumulation and Frequency of Use Note
 * http://www.loc.gov/marc/bibliographic/bd584.html
 */
public class Tag584 extends DataFieldDefinition {

  private static Tag584 uniqueInstance;

  private Tag584() {
    initialize();
    postCreation();
  }

  public static Tag584 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag584();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "584";
    label = "Accumulation and Frequency of Use Note";
    mqTag = "AccumulationAndFrequencyOfUse";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd584.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Accumulation", "R",
      "b", "Frequency of use", "R",
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setMqTag("rdf:value")
      .setFrbrFunctions(UseManage);
    getSubfield("b").setMqTag("frequency")
      .setFrbrFunctions(UseManage);
    getSubfield("3").setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("5").setMqTag("institutionToWhichFieldApplies")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
