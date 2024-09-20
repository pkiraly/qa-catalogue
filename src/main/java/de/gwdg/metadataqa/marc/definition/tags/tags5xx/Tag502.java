package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Dissertation Note
 * https://www.loc.gov/marc/bibliographic/bd502.html
 */
public class Tag502 extends DataFieldDefinition {

  private static Tag502 uniqueInstance;

  private Tag502() {
    initialize();
    postCreation();
  }

  public static Tag502 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag502();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "502";
    label = "Dissertation Note";
    bibframeTag = "Dissertation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd502.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Dissertation note", "NR",
      "b", "Degree type", "NR",
      "c", "Name of granting institution", "NR",
      "d", "Year degree granted", "NR",
      "g", "Miscellaneous information", "R",
      "o", "Dissertation identifier", "R",
      "6", "Linkage", "NR",
      "7", "Data provenance", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setBibframeTag("degree")
      .setCompilanceLevels("O");

    getSubfield("c")
      .setBibframeTag("grantingInstitution")
      .setCompilanceLevels("O");

    getSubfield("d")
      .setBibframeTag("date")
      .setCompilanceLevels("O");

    getSubfield("g")
      .setBibframeTag("note")
      .setCompilanceLevels("O");

    getSubfield("o")
      .setBibframeTag("identifiedBy")
      .setCompilanceLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("7")
      .setMqTag("dataProvenance");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));

    putVersionSpecificSubfields(MarcVersion.HBZ, Arrays.asList(
      new SubfieldDefinition("9", "Feldzuordnung Aleph", "R")
    ));
  }
}
