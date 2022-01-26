package de.gwdg.metadataqa.marc.definition.tags.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

/**
 * Version Information
 * https://www.loc.gov/marc/bibliographic/bd251.html
 */
public class Tag251 extends DataFieldDefinition {
  private static Tag251 uniqueInstance;

  private Tag251() {
    initialize();
    postCreation();
  }

  public static Tag251 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag251();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "251";
    label = "Version Information";
    mqTag = "Version";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd251.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Version", "R",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "2", "Source", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("a")
      .setBibframeTag("version");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance())
      .setCompilanceLevels("O");

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setMqTag("source");

    getSubfield("3")
      .setMqTag("materialsSpecified");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setContentParser(LinkageParser.getInstance());

    getSubfield("8")
      .setMqTag("fieldLink");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR"),
      new SubfieldDefinition("@", "Language of field", "NR"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR")
    ));
  }
}
