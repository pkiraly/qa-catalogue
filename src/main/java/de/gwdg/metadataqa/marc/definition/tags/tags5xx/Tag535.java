package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

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
    setCompilanceLevels("O");

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
      .setCompilanceLevels("M");

    getSubfield("b")
      .setMqTag("postalAddress")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("country")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("telecommunicationsAddress")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("g")
      .setMqTag("repositoryLocation")
      .setFrbrFunctions(DiscoveryObtain)
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

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR"),
      new SubfieldDefinition("@", "Language of field", "NR"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR")
    ));
  }
}
