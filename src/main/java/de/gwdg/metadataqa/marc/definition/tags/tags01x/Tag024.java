package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.codelist.StandardIdentifierSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySearch;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementDisplay;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Other Standard Identifier
 * https://www.loc.gov/marc/bibliographic/bd024.html
 */
public class Tag024 extends DataFieldDefinition {

  private static Tag024 uniqueInstance;

  private Tag024() {
    initialize();
    postCreation();
  }

  public static Tag024 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag024();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "024";
    label = "Other Standard Identifier";
    bibframeTag = "IdentifiedBy";
    mqTag = "OtherStandardIdentifier";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd024.html";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator("Type of standard number or code")
      .setCodes(
        "0", "International Standard Recording Code",
        "1", "Universal Product Code",
        "2", "International Standard Music Number",
        "3", "International Article Number",
        "4", "Serial Item and Contribution Identifier",
        "7", "Source specified in subfield $2",
        "8", "Unspecified type of standard number or code"
      )
      .putVersionSpecificCodes(MarcVersion.SZTE, Arrays.asList(
        new EncodedValue(" ", "Not specified")
      ))
      .setMqTag("type")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess, ManagementDisplay);

    ind2 = new Indicator("Difference indicator")
      .setCodes(
        " ", "No information provided",
        "0", "No difference",
        "1", "Difference"
      )
      .setMqTag("differenceIndicator")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay);

    setSubfieldsWithCardinality(
      "a", "Standard number or code", "NR",
      "c", "Terms of availability", "NR",
      "d", "Additional codes following the standard number or code", "NR",
      "q", "Qualifying information", "R",
      "z", "Canceled/invalid standard number or code", "R",
      "2", "Source of number or code", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(StandardIdentifierSourceCodes.getInstance());
    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("M", "M");

    getSubfield("c")
      .setBibframeTag("acquisitionTerms")
      .setFrbrFunctions(DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("d")
      .setBibframeTag("note")
      .setCompilanceLevels("A", "A");

    getSubfield("q")
      .setBibframeTag("qualifier");

    getSubfield("z")
      .setMqTag("canceledNumber")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("2")
      .setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A", "A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A", "A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    setHistoricalSubfields(
      "b", "Additional codes following the standard number [OBSOLETE]"
    );

    putVersionSpecificSubfields(MarcVersion.DNB, Arrays.asList(
      new SubfieldDefinition("9", "Standardnummer (mit Bindestrichen)", "NR")
    ));

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
