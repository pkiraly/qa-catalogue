package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySearch;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * National Agricultural Library Copy Statement
 * http://www.loc.gov/marc/bibliographic/bd071.html
 */
public class Tag071 extends DataFieldDefinition {

  private static Tag071 uniqueInstance;

  private Tag071() {
    initialize();
    postCreation();
  }

  public static Tag071 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag071();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "071";
    label = "National Agricultural Library Copy Statement";
    mqTag = "NalCopy";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd071.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator()
      .setHistoricalCodes(
        "0", "No series involved",
        "1", "Main series",
        "2", "Subseries",
        "3", "Sub-subseries"
      );

    setSubfieldsWithCardinality(
      "a", "Classification number", "R",
      "b", "Item number", "NR",
      "c", "Copy information", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("a")
      .setBibframeTag("classificationPortion").setMqTag("classification")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setBibframeTag("itemPortion").setMqTag("item")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("copy")
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
