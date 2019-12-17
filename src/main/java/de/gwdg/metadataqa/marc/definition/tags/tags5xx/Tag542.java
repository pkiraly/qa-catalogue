package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Information Relating to Copyright Status
 * http://www.loc.gov/marc/bibliographic/bd542.html
 */
public class Tag542 extends DataFieldDefinition {

  private static Tag542 uniqueInstance;

  private Tag542() {
    initialize();
    postCreation();
  }

  public static Tag542 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag542();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "542";
    label = "Information Relating to Copyright Status";
    mqTag = "CopyrightStatus";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd542.html";
    setLevels("O");

    ind1 = new Indicator("Privacy")
      .setCodes(
        " ", "No information provided",
        "0", "Private",
        "1", "Not private"
      )
      .setMqTag("privacy");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Personal creator", "NR",
      "b", "Personal creator death date", "NR",
      "c", "Corporate creator", "NR",
      "d", "Copyright holder", "R",
      "e", "Copyright holder contact information", "R",
      "f", "Copyright statement", "R",
      "g", "Copyright date", "NR",
      "h", "Copyright renewal date", "R",
      "i", "Publication date", "NR",
      "j", "Creation date", "NR",
      "k", "Publisher", "R",
      "l", "Copyright status", "NR",
      "m", "Publication status", "NR",
      "n", "Note", "R",
      "o", "Research date", "NR",
      "p", "Country of publication or creation", "R",
      "q", "Supplying agency", "NR",
      "r", "Jurisdiction of copyright assessment", "NR",
      "s", "Source of information", "NR",
      "u", "Uniform Resource Identifier", "R",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("personalCreator")
      .setLevels("O");

    getSubfield("b")
      .setMqTag("personalCreatorDeathDate")
      .setLevels("O");

    getSubfield("c")
      .setMqTag("corporateCreator")
      .setLevels("O");

    getSubfield("d")
      .setMqTag("copyrightHolder")
      .setLevels("A");

    getSubfield("e")
      .setMqTag("copyrightHolderContact")
      .setLevels("O");

    getSubfield("f")
      .setMqTag("copyrightStatement")
      .setLevels("A");

    getSubfield("g")
      .setMqTag("copyrightDate")
      .setLevels("A");

    getSubfield("h")
      .setMqTag("copyrightRenewalDate")
      .setLevels("A");

    getSubfield("i")
      .setMqTag("publicationDate")
      .setLevels("O");

    getSubfield("j")
      .setMqTag("creationDate")
      .setLevels("O");

    getSubfield("k")
      .setMqTag("publisher")
      .setLevels("O");

    getSubfield("l")
      .setMqTag("copyrightStatus")
      .setLevels("O");

    getSubfield("m")
      .setMqTag("publicationStatus")
      .setLevels("A");

    getSubfield("n")
      .setMqTag("note")
      .setLevels("O");

    getSubfield("o")
      .setMqTag("researchDate")
      .setLevels("O");

    getSubfield("p")
      .setMqTag("country")
      .setLevels("A");

    getSubfield("q")
      .setMqTag("supplyingAgency")
      .setLevels("O");

    getSubfield("r")
      .setMqTag("jurisdiction")
      .setLevels("A");

    getSubfield("s")
      .setMqTag("source")
      .setLevels("O");

    getSubfield("u")
      .setMqTag("uri")
      .setLevels("O");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setLevels("O");
  }
}
