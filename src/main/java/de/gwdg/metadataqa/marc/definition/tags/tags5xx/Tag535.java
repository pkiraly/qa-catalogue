package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Location of Originals/Duplicates Note
 * http://www.loc.gov/marc/bibliographic/bd535.html
 */
public class Tag535 extends DataFieldDefinition {

  private static Tag535 uniqueInstance;

  private Tag535() {
    initialize();
    postCreation();
  }

  public static Tag535 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag535();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "535";
    label = "Location of Originals/Duplicates Note";
    mqTag = "LocationOfOriginalsOrDuplicates";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd535.html";
    setLevels("O");

    ind1 = new Indicator("Custodial role")
      .setCodes(
        "1", "Holder of originals",
        "2", "Holder of duplicates"
      )
      .setHistoricalCodes(
        "0", "Repository (AM) [OBSOLETE, 1984]",
        "3", "Holder of oral tapes (AM) [OBSOLETE, 1984]"
      )
      .setMqTag("custodialRole");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Custodian", "NR",
      "b", "Postal address", "R",
      "c", "Country", "R",
      "d", "Telecommunications address", "R",
      "g", "Repository location code", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("g").setCodeList(CountryCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("custodian")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setLevels("M");

    getSubfield("b")
      .setMqTag("postalAddress")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setLevels("A");

    getSubfield("c")
      .setMqTag("country")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setLevels("A");

    getSubfield("d")
      .setMqTag("telecommunicationsAddress")
      .setFrbrFunctions(DiscoveryObtain)
      .setLevels("O");

    getSubfield("g")
      .setMqTag("repositoryLocation")
      .setFrbrFunctions(DiscoveryObtain)
      .setLevels("O");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("O");

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
