package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * National Library of Medicine Copy Statement
 * http://www.loc.gov/marc/bibliographic/bd061.html
 */
public class Tag061 extends DataFieldDefinition {

  private static Tag061 uniqueInstance;

  private Tag061() {
    initialize();
    postCreation();
  }

  public static Tag061 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag061();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "061";
    label = "National Library of Medicine Copy Statement";
    mqTag = "NlmCopy";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd061.html";
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
      "c", "Copy information", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("a")
      .setMqTag("classificationPortion").setMqTag("classification")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setMqTag("itemPortion").setMqTag("item")
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
      new SubfieldDefinition("*", "Link with identifier", "NR"),
      new SubfieldDefinition("@", "Language of field", "NR"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR")
    ));
  }
}
