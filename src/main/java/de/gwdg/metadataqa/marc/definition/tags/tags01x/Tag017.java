package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.CopyrightAndLegalDepositNumberSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * CopyrightAnd or Legal Deposit Number
 * http://www.loc.gov/marc/bibliographic/bd017.html
 */
public class Tag017 extends DataFieldDefinition {

  private static Tag017 uniqueInstance;

  private Tag017() {
    initialize();
    postCreation();
  }

  public static Tag017 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag017();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "017";
    label = "Copyright or Legal Deposit Number";
    bibframeTag = "CopyrightNumber";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd017.html";
    setCompilanceLevels("O");

    ind1 = new Indicator()
      .setHistoricalCodes(
        "0", "United States [OBSOLETE]",
        "1", "Canada [OBSOLETE]",
        "2", "France [OBSOLETE] [CAN/MARC only]"
      );

    ind2 = new Indicator("Display constant controller")
      .setCodes(
        " ", "Copyright or legal deposit number",
        "8", "No display constant generated"
      )
      .setMqTag("displayConstant")
      .setFrbrFunctions(ManagementDisplay);

    setSubfieldsWithCardinality(
      "a", "Copyright or legal deposit number", "R",
      "b", "Assigning agency", "NR",
      "d", "Date", "NR",
      "i", "Display text", "NR",
      "z", "Canceled/invalid copyright or legal deposit number", "R",
      "2", "Source", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(CopyrightAndLegalDepositNumberSourceCodes.getInstance());
    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setBibframeTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("M");

    getSubfield("d")
      .setBibframeTag("date")
      .setCompilanceLevels("O");

    getSubfield("i")
      .setBibframeTag("note")
      .setCompilanceLevels("O");

    getSubfield("z")
      .setMqTag("canceled")
      .setCompilanceLevels("A");

    getSubfield("2")
      .setBibframeTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

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
