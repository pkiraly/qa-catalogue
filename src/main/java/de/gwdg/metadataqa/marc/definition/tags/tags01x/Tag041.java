package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodeAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Language Code
 * http://www.loc.gov/marc/bibliographic/bd041.html
 */
public class Tag041 extends DataFieldDefinition {

  private static Tag041 uniqueInstance;

  private Tag041() {
    initialize();
    postCreation();
  }

  public static Tag041 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag041();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "041";
    label = "Language Code";
    bibframeTag = "Language";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd041.html";
    setCompilanceLevels("A");

    ind1 = new Indicator("Translation indication")
      .setCodes(
        " ", "No information provided",
        "0", "Item not a translation/does not include a translation",
        "1", "Item is or includes a translation"
      )
      .setMqTag("translationIndication")
      .setFrbrFunctions(DiscoverySelect);
    ind2 = new Indicator("Source of code")
      .setCodes(
        " ", "MARC language code",
        "7", "Source specified in subfield $2"
      )
      .setMqTag("sourceOfCode")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    setSubfieldsWithCardinality(
      "a", "Language code of text/sound track or separate title", "R",
      "b", "Language code of summary or abstract", "R",
      "d", "Language code of sung or spoken text", "R",
      "e", "Language code of librettos", "R",
      "f", "Language code of table of contents", "R",
      "g", "Language code of accompanying material other than librettos and transcripts", "R",
      "h", "Language code of original", "R",
      "i", "Language code of intertitles", "R",
      "j", "Language code of subtitles", "R",
      "k", "Language code of intermediate translations", "R",
      "m", "Language code of original accompanying materials other than librettos", "R",
      "n", "Language code of original libretto", "R",
      "p", "Language code of captions", "R",
      "q", "Language code of accessible audio", "R",
      "r", "Language code of accessible visual language (non-textual)", "R",
      "t", "Language code of accompanying transcripts for audiovisual materials", "R",
      "2", "Source of code", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("a").setCodeList(LanguageCodes.getInstance());
    getSubfield("b").setCodeList(LanguageCodes.getInstance());
    getSubfield("d").setCodeList(LanguageCodes.getInstance());
    getSubfield("e").setCodeList(LanguageCodes.getInstance());
    getSubfield("f").setCodeList(LanguageCodes.getInstance());
    getSubfield("g").setCodeList(LanguageCodes.getInstance());
    getSubfield("h").setCodeList(LanguageCodes.getInstance());
    getSubfield("i").setCodeList(LanguageCodes.getInstance());
    getSubfield("j").setCodeList(LanguageCodes.getInstance());
    getSubfield("k").setCodeList(LanguageCodes.getInstance());
    getSubfield("m").setCodeList(LanguageCodes.getInstance());
    getSubfield("n").setCodeList(LanguageCodes.getInstance());
    getSubfield("p").setCodeList(LanguageCodes.getInstance());
    getSubfield("q").setCodeList(LanguageCodes.getInstance());
    getSubfield("r").setCodeList(LanguageCodes.getInstance());
    getSubfield("2").setCodeList(LanguageCodeAndTermSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdf:value")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseInterpret)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setMqTag("ofSummary")
      .setFrbrFunctions(DiscoverySelect, UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("ofSungOrSpokenText")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("e")
      .setMqTag("ofLibrettos")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("f")
      .setMqTag("ofTableOfContents")
      .setFrbrFunctions(DiscoverySelect, UseInterpret)
      .setCompilanceLevels("O");

    getSubfield("g")
      .setMqTag("ofAccompanyingMaterial")
      .setFrbrFunctions(DiscoverySelect, UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("h")
      .setMqTag("ofOriginal")
      .setFrbrFunctions(DiscoverySelect)
      .setCompilanceLevels("A");

    getSubfield("i")
      .setMqTag("ofIntertitles");

    getSubfield("j")
      .setMqTag("ofSubtitles")
      .setCompilanceLevels("A");

    getSubfield("k")
      .setMqTag("ofTranslations");

    getSubfield("m")
      .setMqTag("ofOriginalAccompanyingMaterial");

    getSubfield("n")
      .setMqTag("ofOriginalLibretto");

    getSubfield("p")
      .setMqTag("ofCaptions");

    getSubfield("q")
      .setMqTag("ofAccessibleAudio");

    getSubfield("r")
      .setMqTag("ofAccessibleVisualLanguage");

    getSubfield("t")
      .setMqTag("ofTranscripts");

    getSubfield("2")
      .setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    setHistoricalSubfields(
      "c", "Languages of separate titles (VM) [OBSOLETE, 1972], Languages of available translation (SE) [OBSOLETE, 1977]"
    );

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR"),
      new SubfieldDefinition("@", "Language of field", "NR"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR")
    ));
  }
}
