package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementDisplay;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Study Program Information Note
 * https://www.loc.gov/marc/bibliographic/bd526.html
 */
public class Tag526 extends DataFieldDefinition {

  private static Tag526 uniqueInstance;

  private Tag526() {
    initialize();
    postCreation();
  }

  public static Tag526 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag526();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "526";
    label = "Study Program Information Note";
    mqTag = "StudyProgram";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd526.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Display constant controller")
      .setCodes(
        "0", "Reading program",
        "8", "No display constant generated"
      )
      .setMqTag("displayConstant")
      .setFrbrFunctions(ManagementDisplay);

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Program name", "NR",
      "b", "Interest level", "NR",
      "c", "Reading level", "NR",
      "d", "Title point value", "NR",
      "i", "Display text", "NR",
      "x", "Nonpublic note", "R",
      "z", "Public note", "R",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("5").setCodeList(OrganizationCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("studyProgramName")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setBibframeTag("interestLevel")
      .setFrbrFunctions(DiscoverySelect)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setBibframeTag("readingLevel")
      .setFrbrFunctions(DiscoverySelect)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setBibframeTag("titlePoint")
      .setFrbrFunctions(DiscoverySelect)
      .setCompilanceLevels("A");

    getSubfield("i")
      .setBibframeTag("displayText")
      .setCompilanceLevels("O");

    getSubfield("x")
      .setBibframeTag("nonpublicNote")
      .setCompilanceLevels("O");

    getSubfield("z")
      .setBibframeTag("publicNote")
      .setCompilanceLevels("O");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay)
      .setCompilanceLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
