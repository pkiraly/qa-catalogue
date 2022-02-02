package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISBNValidator;
import de.gwdg.metadataqa.marc.definition.general.validator.ISSNValidator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

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
    setCompilanceLevels("A");

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

    getSubfield("x").setValidator(ISSNValidator.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("mainEntry")
      .setCompilanceLevels("A");

    getSubfield("b")
      .setMqTag("edition")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("publication")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("e")
      .setMqTag("physicalDescription")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("f")
      .setMqTag("series")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("k")
      .setMqTag("keyTitle")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("l")
      .setMqTag("location")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("m")
      .setMqTag("material")
      .setCompilanceLevels("A");

    getSubfield("n")
      .setMqTag("note")
      .setCompilanceLevels("O");

    getSubfield("o")
      .setMqTag("otherIdentifier")
      .setCompilanceLevels("O");

    getSubfield("p")
      .setMqTag("introductoryPhrase")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("M");

    getSubfield("t")
      .setMqTag("title")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("x")
      .setMqTag("issn")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("z")
      .setMqTag("isbn")
      .setValidator(ISBNValidator.getInstance())
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("3")
      .setMqTag("materialsSpecified")
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
