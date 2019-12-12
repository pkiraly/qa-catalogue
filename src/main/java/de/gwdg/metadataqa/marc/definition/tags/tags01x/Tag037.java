package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Source of Acquisition
 * http://www.loc.gov/marc/bibliographic/bd037.html
 */
public class Tag037 extends DataFieldDefinition {

  private static Tag037 uniqueInstance;

  private Tag037() {
    initialize();
    postCreation();
  }

  public static Tag037 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag037();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "037";
    label = "Source of Acquisition";
    bibframeTag = "AcquisitionSource";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";

    ind1 = new Indicator("Source of acquisition sequence")
      .setCodes(
        " ", "Not applicable/No information provided/Earliest",
        "2", "Intervening",
        "3", "Current/Latest"
      )
      .setMqTag("source");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Stock number", "NR",
      "b", "Source of stock number/acquisition", "NR",
      "c", "Terms of availability", "R",
      "f", "Form of issue", "R",
      "g", "Additional format characteristics", "R",
      "n", "Note", "R",
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("stockNumber")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setLevels("A");

    getSubfield("b")
      .setBibframeTag("rdfs:label")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("M");

    getSubfield("c")
      .setBibframeTag("acquisitionTerms")
      .setFrbrFunctions(DiscoverySelect, DiscoveryObtain)
      .setLevels("O");

    getSubfield("f")
      .setBibframeTag("note").setMqTag("formOfIssue")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage, UseOperate)
      .setLevels("O");

    getSubfield("g")
      .setBibframeTag("note").setMqTag("format")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage, UseOperate)
      .setLevels("O");

    getSubfield("n")
      .setBibframeTag("note")
      .setLevels("O");

    getSubfield("3")
      .setMqTag("materialsSpecified");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies");

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
