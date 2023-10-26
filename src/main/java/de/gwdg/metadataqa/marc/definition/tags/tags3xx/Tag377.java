package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodeAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

import java.util.Arrays;

/**
 * Associated Language
 * http://www.loc.gov/marc/bibliographic/bd377.html
 */
public class Tag377 extends DataFieldDefinition {
  private static Tag377 uniqueInstance;

  private Tag377() {
    initialize();
    postCreation();
  }

  public static Tag377 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag377();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "377";
    label = "Associated Language";
    mqTag = "AssociatedLanguage";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd377.html";

    ind1 = new Indicator();
    ind2 = new Indicator("Source of code")
      .setCodes(
        " ", "MARC language code",
        "7", "Source specified in $2"
      )
      .setMqTag("sourceOfCode");

    setSubfieldsWithCardinality(
      "a", "Language code", "R",
      "l", "Language term", "R",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "2", "Source", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "7", "Data provenance", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("languageCode");

    getSubfield("l")
      .setMqTag("languageTerm");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setMqTag("source")
      .setCodeList(LanguageCodeAndTermSourceCodes.getInstance());

    getSubfield("3")
      .setMqTag("materials");

    getSubfield("6")
      .setBibframeTag("linkage");

    getSubfield("7")
      .setMqTag("dataProvenance");

    getSubfield("8")
      .setMqTag("fieldLink");

    putVersionSpecificSubfields(MarcVersion.NKCR, Arrays.asList(
      new SubfieldDefinition("7", "NKCR Authority ID", "NR")
    ));

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
