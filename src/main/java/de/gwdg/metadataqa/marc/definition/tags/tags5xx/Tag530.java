package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Additional Physical Form available Note
 * http://www.loc.gov/marc/bibliographic/bd530.html
 */
public class Tag530 extends DataFieldDefinition {

  private static Tag530 uniqueInstance;

  private Tag530() {
    initialize();
    postCreation();
  }

  public static Tag530 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag530();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "530";
    label = "Additional Physical Form available Note";
    mqTag = "AdditionalPhysicalFormAvailable";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd530.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Additional physical form available note", "NR",
      "b", "Availability source", "NR",
      "c", "Availability conditions", "NR",
      "d", "Order number", "NR",
      "u", "Uniform Resource Identifier", "R",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setMqTag("source")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("conditions")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("orderNumber")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("u")
      .setMqTag("uri")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    setHistoricalSubfields(
      "z", "Source of note information (AM, CF, VM, SE) [OBSOLETE, 1990]"
    );

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR"),
      new SubfieldDefinition("@", "Language of field", "NR"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR")
    ));
  }
}
