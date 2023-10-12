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
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseInterpret;

/**
 * Entity and Attribute Information Note
 * http://www.loc.gov/marc/bibliographic/bd552.html
 */
public class Tag552 extends DataFieldDefinition {

  private static Tag552 uniqueInstance;

  private Tag552() {
    initialize();
    postCreation();
  }

  public static Tag552 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag552();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "552";
    label = "Entity and Attribute Information Note";
    mqTag = "EntityAndAttributeInformation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd552.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Entity type label", "NR",
      "b", "Entity type definition and source", "NR",
      "c", "Attribute label", "NR",
      "d", "Attribute definition and source", "NR",
      "e", "Enumerated domain value", "R",
      "f", "Enumerated domain value definition and source", "R",
      "g", "Range domain minimum and maximum", "NR",
      "h", "Codeset name and source", "NR",
      "i", "Unrepresentable domain", "NR",
      "j", "Attribute units of measurement and resolution", "NR",
      "k", "Beginning and ending date of attribute values", "NR",
      "l", "Attribute value accuracy", "NR",
      "m", "Attribute value accuracy explanation", "NR",
      "n", "Attribute measurement frequency", "NR",
      "o", "Entity and attribute overview", "R",
      "p", "Entity and attribute detail citation", "R",
      "u", "Uniform Resource Identifier", "R",
      "z", "Display note", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("entityType")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("b")
      .setMqTag("entityTypeDefinition")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("attribute")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("attributeDefinition")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("e")
      .setMqTag("enumeratedDomainValue")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("f")
      .setMqTag("enumeratedDomainValueDefinition")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("g")
      .setMqTag("range")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("h")
      .setMqTag("codeset")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("i")
      .setMqTag("unrepresentableDomain")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("j")
      .setMqTag("attributeUnits")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("k")
      .setMqTag("date")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("l")
      .setMqTag("attributeValueAccuracy")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("m")
      .setMqTag("attributeValueAccuracyExplanation")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("n")
      .setMqTag("attributeMeasurementFrequency")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("o")
      .setMqTag("overview")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("p")
      .setMqTag("detailCitation")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("u")
      .setMqTag("uri")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("z")
      .setMqTag("displayNote")
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
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
