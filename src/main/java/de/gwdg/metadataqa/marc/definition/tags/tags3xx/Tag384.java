package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

/**
 * Key
 * https://www.loc.gov/marc/bibliographic/bd384.html
 */
public class Tag384 extends DataFieldDefinition {
  private static Tag384 uniqueInstance;

  private Tag384() {
    initialize();
    postCreation();
  }

  public static Tag384 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag384();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "384";
    label = "Key";
    mqTag = "Key";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd384.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Key type")
      .setCodes(
        " ", "Relationship to original unknown",
        "0", "Original key",
        "1", "Transposed key",
        "2", "Key of representative expression"
      )
      .setMqTag("type");
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Key", "NR",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "7", "Data provenance", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setCompilanceLevels("M");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("3")
      .setBibframeTag("materials");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setCompilanceLevels("A");

    getSubfield("7")
      .setMqTag("dataProvenance");

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
