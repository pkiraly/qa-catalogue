package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseManage;

/**
 * Dates of Publication and/or Sequential Designation
 * https://www.loc.gov/marc/bibliographic/bd362.html
 */
public class Tag362 extends DataFieldDefinition {
  private static Tag362 uniqueInstance;

  private Tag362() {
    initialize();
    postCreation();
  }

  public static Tag362 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag362();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "362";
    label = "Dates of Publication and/or Sequential Designation";
    mqTag = "DatesOfPublication";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd362.html";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator("Format of date")
      .setCodes(
        "0", "Formatted style",
        "1", "Unformatted note"
      )
      .setMqTag("format")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Dates of publication and/or sequential designation", "NR",
      "z", "Source of information", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("M", "M");

    getSubfield("z")
      .setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A", "A");

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
