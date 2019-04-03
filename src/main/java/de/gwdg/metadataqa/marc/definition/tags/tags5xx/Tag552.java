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

    getSubfield("a").setMqTag("entityType")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("b").setMqTag("entityTypeDefinition")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("c").setMqTag("attribute")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("d").setMqTag("attributeDefinition")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("e").setMqTag("enumeratedDomainValue")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("f").setMqTag("enumeratedDomainValueDefinition")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("g").setMqTag("range")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("h").setMqTag("codeset")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("i").setMqTag("unrepresentableDomain")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("j").setMqTag("attributeUnits")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("k").setMqTag("date")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("l").setMqTag("attributeValueAccuracy")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("m").setMqTag("attributeValueAccuracyExplanation")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("n").setMqTag("attributeMeasurementFrequency")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("o").setMqTag("overview")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("p").setMqTag("detailCitation")
      .setFrbrFunctions(UsageInterpret);
    getSubfield("u").setMqTag("uri")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
