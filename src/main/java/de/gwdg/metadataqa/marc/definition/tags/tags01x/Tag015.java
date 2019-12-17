package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.NationalBibliographyNumberSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * National Bibliography Number
 * http://www.loc.gov/marc/bibliographic/bd015.html
 */
public class Tag015 extends DataFieldDefinition {

  private static Tag015 uniqueInstance;

  private Tag015() {
    initialize();
    postCreation();
  }

  public static Tag015 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag015();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "015";
    label = "National Bibliography Number";
    bibframeTag = "IdentifiedBy/Nbn";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd015.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "National bibliography number", "R",
      "q", "Qualifying information", "R",
      "z", "Canceled/invalid national bibliography number", "R",
      "2", "Source", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(NationalBibliographyNumberSourceCodes.getInstance());
    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdf:value")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("M");

    getSubfield("q")
      .setBibframeTag("qualifier");

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
  }
}
