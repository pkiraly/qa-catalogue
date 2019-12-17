package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Synthesized Classification Number Components
 * http://www.loc.gov/marc/bibliographic/bd085.html
 */
public class Tag085 extends DataFieldDefinition {

  private static Tag085 uniqueInstance;

  private Tag085() {
    initialize();
    postCreation();
  }

  public static Tag085 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag085();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "085";
    label = "Synthesized Classification Number Components";
    mqTag = "SynthesizedClassificationNumber";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd085.html";
    setLevels("A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Number where instructions are found-single number or beginning number of span", "R",
      "b", "Base number", "R",
      "c", "Classification number-ending number of span", "R",
      "f", "Facet designator", "R",
      "r", "Root number", "R",
      "s", "Digits added from classification number in schedule or external table", "R",
      "t", "Digits added from internal subarrangement or add table", "R",
      "u", "Number being analyzed", "R",
      "v", "Number in internal subarrangement or add table where instructions are found", "R",
      "w", "Table identification-Internal subarrangement or add table", "R",
      "y", "Table sequence number for internal subarrangement or add table", "R",
      "z", "Table identification", "R",
      "0", "Authority record control number or standard number", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("classificationPortion")
      .setLevels("O");

    getSubfield("b")
      .setMqTag("baseNumber")
      .setLevels("A");

    getSubfield("c")
      .setMqTag("endingNumber")
      .setLevels("A");

    getSubfield("f")
      .setMqTag("facet")
      .setLevels("A");

    getSubfield("r")
      .setMqTag("rootNumber")
      .setLevels("A");

    getSubfield("s")
      .setMqTag("fromClassification")
      .setLevels("A");

    getSubfield("t")
      .setMqTag("subarrangement")
      .setLevels("A");

    getSubfield("u")
      .setMqTag("analyzedNumber")
      .setLevels("O");

    getSubfield("v")
      .setMqTag("numberInSubarrangement")
      .setLevels("A");

    getSubfield("w")
      .setMqTag("tableIdentificationInternal")
      .setLevels("A");

    getSubfield("y")
      .setMqTag("tableSequenceNumber")
      .setLevels("A");

    getSubfield("z")
      .setMqTag("tableIdentification")
      .setLevels("A");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setLevels("O");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setLevels("O");
  }
}
