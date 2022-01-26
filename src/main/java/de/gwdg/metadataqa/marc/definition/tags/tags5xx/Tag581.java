package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISBNValidator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Publications About Described Materials Note
 * http://www.loc.gov/marc/bibliographic/bd581.html
 */
public class Tag581 extends DataFieldDefinition {

  private static Tag581 uniqueInstance;

  private Tag581() {
    initialize();
    postCreation();
  }

  public static Tag581 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag581();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "581";
    label = "Publications About Described Materials Note";
    mqTag = "PublicationsAboutDescribedMaterials";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd581.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Display constant controller")
      .setCodes(
        " ", "Publications",
        "8", "No display constant generated"
      )
      .setMqTag("displayConstant")
      .setFrbrFunctions(ManagementDisplay);

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Publications about described materials note", "NR",
      "z", "International Standard Book Number", "R",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("M");

    getSubfield("z")
      .setMqTag("isbn")
      .setValidator(ISBNValidator.getInstance())
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

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
