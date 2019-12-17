package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

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
    setLevels("O");

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
      .setLevels("A");

    getSubfield("b")
      .setMqTag("entityTypeDefinition")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("c")
      .setMqTag("attribute")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("d")
      .setMqTag("attributeDefinition")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("e")
      .setMqTag("enumeratedDomainValue")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("f")
      .setMqTag("enumeratedDomainValueDefinition")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("g")
      .setMqTag("range")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("h")
      .setMqTag("codeset")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("i")
      .setMqTag("unrepresentableDomain")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("j")
      .setMqTag("attributeUnits")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("k")
      .setMqTag("date")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("l")
      .setMqTag("attributeValueAccuracy")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("m")
      .setMqTag("attributeValueAccuracyExplanation")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("n")
      .setMqTag("attributeMeasurementFrequency")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("o")
      .setMqTag("overview")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("p")
      .setMqTag("detailCitation")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("u")
      .setMqTag("uri")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setLevels("O");

    getSubfield("z")
      .setMqTag("displayNote")
      .setLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("O");
  }
}
