package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Study Program Information Note
 * http://www.loc.gov/marc/bibliographic/bd526.html
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
    setLevels("O");

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
      .setLevels("M");

    getSubfield("b")
      .setBibframeTag("interestLevel")
      .setFrbrFunctions(DiscoverySelect)
      .setLevels("A");

    getSubfield("c")
      .setBibframeTag("readingLevel")
      .setFrbrFunctions(DiscoverySelect)
      .setLevels("A");

    getSubfield("d")
      .setBibframeTag("titlePoint")
      .setFrbrFunctions(DiscoverySelect)
      .setLevels("A");

    getSubfield("i")
      .setBibframeTag("displayText")
      .setLevels("O");

    getSubfield("x")
      .setBibframeTag("nonpublicNote")
      .setLevels("O");

    getSubfield("z")
      .setBibframeTag("publicNote")
      .setLevels("O");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay)
      .setLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("O");
  }
}
