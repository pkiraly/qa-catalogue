package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.YYYYMMDDDateParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Patent Control Information
 * http://www.loc.gov/marc/bibliographic/bd013.html
 */
public class Tag013 extends DataFieldDefinition {

  private static Tag013 uniqueInstance;

  private Tag013() {
    initialize();
    postCreation();
  }

  public static Tag013 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag013();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "013";
    label = "Patent Control Information";
    mqTag = "PatentControl";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd013.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Number", "NR",
      "b", "Country", "NR",
      "c", "Type of number", "NR",
      "d", "Date", "R",
      "e", "Status", "R",
      "f", "Party to document", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("b").setCodeList(CountryCodes.getInstance());
    // TODO
    // $f - Codes from: MARC Code List for Countries and MARC Code List for Organizations.
    getSubfield("f").setCodeList(OrganizationCodes.getInstance());
    getSubfield("d").setContentParser(YYYYMMDDDateParser.getInstance());
    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("number")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("b")
      .setMqTag("country")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("type")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("date")
      .setCompilanceLevels("O");

    getSubfield("e")
      .setMqTag("status")
      .setCompilanceLevels("O");

    getSubfield("f")
      .setMqTag("partyToDocument")
      .setCompilanceLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
