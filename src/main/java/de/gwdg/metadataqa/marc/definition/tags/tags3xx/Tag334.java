package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.codelist.GenreFormCodeAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

/**
 * Mode of Issuance
 * https://www.loc.gov/marc/bibliographic/bd334.html
 */
public class Tag334 extends DataFieldDefinition {
  private static Tag334 uniqueInstance;

  private Tag334() {
    initialize();
    postCreation();
  }

  public static Tag334 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag334();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "334";
    label = "Mode of Issuance";
    mqTag = "ModeOfIssuance";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd334.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Mode of issuance term", "NR",
      "b", "Mode of issuance code", "NR",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "2", "Source", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("a")
      .setMqTag("modeOfIssuanceTerm");

    getSubfield("b")
      .setMqTag("modeOfIssuanceCode");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setBibframeTag("source")
      .setCodeList(GenreFormCodeAndTermSourceCodes.getInstance());

    getSubfield("6")
      .setBibframeTag("linkage")
      .setContentParser(LinkageParser.getInstance());

    getSubfield("8")
      .setMqTag("fieldLink");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
