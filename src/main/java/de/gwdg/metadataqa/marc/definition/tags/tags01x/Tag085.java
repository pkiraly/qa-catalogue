package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

import java.util.Arrays;

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
    setCompilanceLevels("A");

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
      .setCompilanceLevels("O");

    getSubfield("b")
      .setMqTag("baseNumber")
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("endingNumber")
      .setCompilanceLevels("A");

    getSubfield("f")
      .setMqTag("facet")
      .setCompilanceLevels("A");

    getSubfield("r")
      .setMqTag("rootNumber")
      .setCompilanceLevels("A");

    getSubfield("s")
      .setMqTag("fromClassification")
      .setCompilanceLevels("A");

    getSubfield("t")
      .setMqTag("subarrangement")
      .setCompilanceLevels("A");

    getSubfield("u")
      .setMqTag("analyzedNumber")
      .setCompilanceLevels("O");

    getSubfield("v")
      .setMqTag("numberInSubarrangement")
      .setCompilanceLevels("A");

    getSubfield("w")
      .setMqTag("tableIdentificationInternal")
      .setCompilanceLevels("A");

    getSubfield("y")
      .setMqTag("tableSequenceNumber")
      .setCompilanceLevels("A");

    getSubfield("z")
      .setMqTag("tableIdentification")
      .setCompilanceLevels("A");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("6")
      .setBibframeTag("linkage")
      .setCompilanceLevels("O");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.NKCR, Arrays.asList(
      new SubfieldDefinition("7", "NKCR Authority ID", "NR")
    ));
  }
}
