package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementDisplay;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Formatted Contents Note
 * https://www.loc.gov/marc/bibliographic/bd505.html
 */
public class Tag505 extends DataFieldDefinition {

  private static Tag505 uniqueInstance;

  private Tag505() {
    initialize();
    postCreation();
  }

  public static Tag505 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag505();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "505";
    label = "Formatted Contents Note";
    bibframeTag = "TableOfContents";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd505.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Display constant controller")
      .setCodes(
        "0", "Contents",
        "1", "Incomplete contents",
        "2", "Partial contents",
        "8", "No display constant generated"
      )
      .setMqTag("displayConstant")
      .setFrbrFunctions(ManagementDisplay);

    ind2 = new Indicator("Level of content designation")
      .setCodes(
        " ", "Basic",
        "0", "Enhanced"
      )
      .setMqTag("level")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay);

    setSubfieldsWithCardinality(
      "a", "Formatted contents note", "NR",
      "g", "Miscellaneous information", "R",
      "r", "Statement of responsibility", "R",
      "t", "Title", "R",
      "u", "Uniform Resource Identifier", "R",
      "6", "Linkage", "NR",
      "7", "Data provenance", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySelect)
      .setCompilanceLevels("A");

    getSubfield("g")
      .setMqTag("miscellaneous")
      .setFrbrFunctions(DiscoverySelect)
      .setCompilanceLevels("A");

    getSubfield("r")
      .setMqTag("responsibility")
      .setFrbrFunctions(DiscoverySelect)
      .setCompilanceLevels("A");

    getSubfield("t")
      .setMqTag("title")
      .setFrbrFunctions(DiscoverySelect)
      .setCompilanceLevels("A");

    getSubfield("u")
      .setMqTag("uri")
      .setFrbrFunctions(DiscoveryObtain)
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
  }
}
