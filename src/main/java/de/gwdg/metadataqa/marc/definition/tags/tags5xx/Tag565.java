package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Case File Characteristics Note
 * http://www.loc.gov/marc/bibliographic/bd565.html
 */
public class Tag565 extends DataFieldDefinition {

  private static Tag565 uniqueInstance;

  private Tag565() {
    initialize();
    postCreation();
  }

  public static Tag565 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag565();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "565";
    label = "Case File Characteristics Note";
    mqTag = "CaseFileCharacteristics";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd565.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Display constant controller")
      .setCodes(
        " ", "File size",
        "0", "Case file characteristics",
        "8", "No display constant generated"
      )
      .setMqTag("displayConstant")
      .setFrbrFunctions(ManagementDisplay);

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Number of cases/variables", "NR",
      "b", "Name of variable", "R",
      "c", "Unit of analysis", "R",
      "d", "Universe of data", "R",
      "e", "Filing scheme or code", "R",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("numberOfCases")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setMqTag("variableName")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("analysisUnit")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("universeOfData")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("O");

    getSubfield("e")
      .setMqTag("filingScheme")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("O");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
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
      new SubfieldDefinition("*", "Link with identifier", "NR"),
      new SubfieldDefinition("@", "Language of field", "NR"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR")
    ));
  }
}
