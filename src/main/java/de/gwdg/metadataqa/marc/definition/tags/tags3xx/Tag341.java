package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.codelist.AccessibilityContentSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

/**
 * Accessibility Content
 * https://www.loc.gov/marc/bibliographic/bd341.html
 */
public class Tag341 extends DataFieldDefinition {
  private static Tag341 uniqueInstance;

  private Tag341() {
    initialize();
    postCreation();
  }

  public static Tag341 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag341();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "341";
    label = "Accessibility Content";
    mqTag = "AccessibilityContent";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd341.html";

    ind1 = new Indicator("Application")
      .setCodes(
        " ", "No information provided",
        "0", "Adaptive features to access primary content",
        "1", "Adaptive features to access secondary content"
      )
      .setMqTag("application");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Content access mode", "NR",
      "b", "Textual assistive features", "R",
      "c", "Visual assistive features", "R",
      "d", "Auditory assistive features", "R",
      "e", "Tactile assistive features", "R",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "2", "Source", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("a")
      .setBibframeTag("accessMode");

    getSubfield("b")
      .setBibframeTag("textualAssistiveFeatures");

    getSubfield("c")
      .setBibframeTag("visualAssistiveFeatures");

    getSubfield("d")
      .setMqTag("auditoryAssistiveFeatures");

    getSubfield("e")
      .setMqTag("tactileAssistiveFeatures");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setBibframeTag("source")
      .setCodeList(AccessibilityContentSourceCodes.getInstance());

    getSubfield("3")
      .setMqTag("materialsSpecified");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setContentParser(LinkageParser.getInstance());

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
