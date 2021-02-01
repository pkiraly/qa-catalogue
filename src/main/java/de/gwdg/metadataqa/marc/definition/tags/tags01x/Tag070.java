package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * National Agricultural Library Call Number
 * http://www.loc.gov/marc/bibliographic/bd070.html
 */
public class Tag070 extends DataFieldDefinition {

  private static Tag070 uniqueInstance;

  private Tag070() {
    initialize();
    postCreation();
  }

  public static Tag070 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag070();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "070";
    label = "National Agricultural Library Call Number";
    bibframeTag = "Classification";
    mqTag = "NalCallNumber";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd070.html";
    setCompilanceLevels("O");

    ind1 = new Indicator(" collection")
      .setCodes(
        "0", "Item is in NAL",
        "1", "Item is not in NAL"
      )
      .setMqTag("existenceInNAL")
      .setFrbrFunctions(DiscoverySelect, DiscoveryObtain);

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
      "0", "Authority record control number or standard number", "R",
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

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.NKCR, Arrays.asList(
      new SubfieldDefinition("7", "NKCR Authority ID", "NR")
    ));
  }
}
