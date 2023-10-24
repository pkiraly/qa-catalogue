package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

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
 * Hours, etc.
 * http://www.loc.gov/marc/bibliographic/bd307.html
 */
public class Tag307 extends DataFieldDefinition {
  private static Tag307 uniqueInstance;

  private Tag307() {
    initialize();
    postCreation();
  }

  public static Tag307 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag307();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "307";
    label = "Hours, etc.";
    mqTag = "Hours";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd307.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Display constant controller")
      .setCodes(
        " ", "Hours",
        "8", "No display constant generated"
      )
      .setMqTag("displayConstant")
      .setFrbrFunctions(ManagementDisplay);

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Hours", "NR",
      "b", "Additional information", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    // TODO: $a is complex := <days of the week>, <hours>[;]
    // ';' if there is $b
    // <days of the week> :=
    // Sunday   Su
    // Monday   M
    // Tuesday   Tu
    // Wednesday   W
    // Thursday   Th
    // Friday   F
    // Saturday   Sa

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setMqTag("additionalInformation")
      .setCompilanceLevels("O");

    getSubfield("6")
      .setMqTag("linkage")
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
