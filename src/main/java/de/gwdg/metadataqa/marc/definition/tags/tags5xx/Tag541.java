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
 * Immediate Source of Acquisition Note
 * http://www.loc.gov/marc/bibliographic/bd541.html
 */
public class Tag541 extends DataFieldDefinition {

  private static Tag541 uniqueInstance;

  private Tag541() {
    initialize();
    postCreation();
  }

  public static Tag541 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag541();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "541";
    label = "Immediate Source of Acquisition Note";
    bibframeTag = "ImmediateAcquisition";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd541.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Privacy")
      .setCodes(
        " ", "No information provided",
        "0", "Private",
        "1", "Not private"
      )
      .setMqTag("privacy");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Source of acquisition", "NR",
      "b", "Address", "NR",
      "c", "Method of acquisition", "NR",
      "d", "Date of acquisition", "NR",
      "e", "Accession number", "NR",
      "f", "Owner", "NR",
      "h", "Purchase price", "NR",
      "n", "Extent", "R",
      "o", "Type of unit", "R",
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("b")
      .setMqTag("address")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("method")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("date")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("e")
      .setMqTag("accessionNumber")
      .setFrbrFunctions(DiscoveryIdentify, UseManage)
      .setCompilanceLevels("A");

    getSubfield("f")
      .setMqTag("owner")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("h")
      .setMqTag("price")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("O");

    getSubfield("n")
      .setMqTag("extent")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("o")
      .setMqTag("typeOfUnit")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay)
      .setCompilanceLevels("A");

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
