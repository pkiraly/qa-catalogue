package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISBNValidator;
import de.gwdg.metadataqa.marc.definition.general.validator.ISSNValidator;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Original Version Note
 * http://www.loc.gov/marc/bibliographic/bd534.html
 */
public class Tag534 extends DataFieldDefinition {

  private static Tag534 uniqueInstance;

  private Tag534() {
    initialize();
    postCreation();
  }

  public static Tag534 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag534();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "534";
    label = "Original Version Note";
    mqTag = "OriginalVersion";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd534.html";

    ind1 = new Indicator()
      .setHistoricalCodes(
        "0", "Note exclude series of original",
        "1", "Note includes series of original"
      );

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Main entry of original", "NR",
      "b", "Edition statement of original", "NR",
      "c", "Publication, distribution, etc. of original", "NR",
      "e", "Physical description, etc. of original", "NR",
      "f", "Series statement of original", "R",
      "k", "Key title of original", "R",
      "l", "Location of original", "NR",
      "m", "Material specific details", "NR",
      "n", "Note about original", "R",
      "o", "Other resource identifier", "R",
      "p", "Introductory phrase", "NR",
      "t", "Title statement of original", "NR",
      "x", "International Standard Serial Number", "R",
      "z", "International Standard Book Number", "R",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("z").setValidator(ISBNValidator.getInstance());
    getSubfield("x").setValidator(ISSNValidator.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setMqTag("mainEntry");
    getSubfield("b").setMqTag("edition")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
    getSubfield("c").setMqTag("publication")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
    getSubfield("e").setMqTag("physicalDescription")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
    getSubfield("f").setMqTag("series")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
    getSubfield("k").setMqTag("keyTitle")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("l").setMqTag("location")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("m").setMqTag("material");
    getSubfield("n").setMqTag("note");
    getSubfield("o").setMqTag("otherIdentifier");
    getSubfield("p").setMqTag("introductoryPhrase")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("t").setMqTag("title")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain);
    getSubfield("x").setMqTag("issn")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("z").setMqTag("isbn")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("3").setMqTag("materialsSpecified");
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
  }
}
