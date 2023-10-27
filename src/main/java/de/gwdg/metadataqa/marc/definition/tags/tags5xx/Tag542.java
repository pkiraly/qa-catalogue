package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

/**
 * Information Relating to Copyright Status
 * https://www.loc.gov/marc/bibliographic/bd542.html
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
    setCompilanceLevels("O");

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
      .setCompilanceLevels("O");

    getSubfield("b")
      .setMqTag("personalCreatorDeathDate")
      .setCompilanceLevels("O");

    getSubfield("c")
      .setMqTag("corporateCreator")
      .setCompilanceLevels("O");

    getSubfield("d")
      .setMqTag("copyrightHolder")
      .setCompilanceLevels("A");

    getSubfield("e")
      .setMqTag("copyrightHolderContact")
      .setCompilanceLevels("O");

    getSubfield("f")
      .setMqTag("copyrightStatement")
      .setCompilanceLevels("A");

    getSubfield("g")
      .setMqTag("copyrightDate")
      .setCompilanceLevels("A");

    getSubfield("h")
      .setMqTag("copyrightRenewalDate")
      .setCompilanceLevels("A");

    getSubfield("i")
      .setMqTag("publicationDate")
      .setCompilanceLevels("O");

    getSubfield("j")
      .setMqTag("creationDate")
      .setCompilanceLevels("O");

    getSubfield("k")
      .setMqTag("publisher")
      .setCompilanceLevels("O");

    getSubfield("l")
      .setMqTag("copyrightStatus")
      .setCompilanceLevels("O");

    getSubfield("m")
      .setMqTag("publicationStatus")
      .setCompilanceLevels("A");

    getSubfield("n")
      .setMqTag("note")
      .setCompilanceLevels("O");

    getSubfield("o")
      .setMqTag("researchDate")
      .setCompilanceLevels("O");

    getSubfield("p")
      .setMqTag("country")
      .setCompilanceLevels("A");

    getSubfield("q")
      .setMqTag("supplyingAgency")
      .setCompilanceLevels("O");

    getSubfield("r")
      .setMqTag("jurisdiction")
      .setCompilanceLevels("A");

    getSubfield("s")
      .setMqTag("source")
      .setCompilanceLevels("O");

    getSubfield("u")
      .setMqTag("uri")
      .setCompilanceLevels("O");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setCompilanceLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
