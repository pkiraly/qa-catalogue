package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Copy and Version Identification Note
 * http://www.loc.gov/marc/bibliographic/bd562.html
 */
public class Tag562 extends DataFieldDefinition {

  private static Tag562 uniqueInstance;

  private Tag562() {
    initialize();
    postCreation();
  }

  public static Tag562 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag562();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "562";
    label = "Copy and Version Identification Note";
    mqTag = "CopyAndVersionIdentification";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd562.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Identifying markings", "R",
      "b", "Copy identification", "R",
      "c", "Version identification", "R",
      "d", "Presentation format", "R",
      "e", "Number of copies", "R",
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("identifyingMarkings")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("A");

    getSubfield("b")
      .setMqTag("copyIdentification")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("A");

    getSubfield("c")
      .setMqTag("versionIdentification")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("A");

    getSubfield("d")
      .setMqTag("presentationFormat")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect)
      .setLevels("A");

    getSubfield("e")
      .setMqTag("numberOfCopies")
      .setLevels("A");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("O");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay)
      .setLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("O");
  }
}
