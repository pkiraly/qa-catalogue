package de.gwdg.metadataqa.marc.definition.tags.tags76x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISSNValidator;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

/**
 * Other Relationship Entry
 * http://www.loc.gov/marc/bibliographic/bd788.html
 */
public class Tag788 extends DataFieldDefinition {

  private static Tag788 uniqueInstance;

  private Tag788() {
    initialize();
    postCreation();
  }

  public static Tag788 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag788();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "788";
    label = "Parallel Description in Another Language of Cataloging";
    mqTag = "ParallelDescriptionInAnotherLanguage";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd788.html";

    ind1 = new Indicator("Note controller")
      .setCodes(
        "0", "Display note",
        "1", "Do not display note"
      )
      .setMqTag("noteController");

    ind2 = new Indicator("Display constant controller")
      .setCodes(
        " ", "Parallel description in another language of cataloging",
        "8", "No display constant generated"
      )
      .setMqTag("displayConstant");

    setSubfieldsWithCardinality(
      "a", "Main entry heading", "NR",
      "b", "Edition", "NR",
      "d", "Place, publisher, and date of publication", "NR",
      "e", "Language of cataloging", "NR",
      "i", "Relationship information", "R",
      "l", "Data provenance", "R",
      "n", "Note", "R",
      "s", "Uniform title", "NR",
      "t", "Title", "NR",
      "w", "Record control number", "R",
      "x", "International Standard Serial Number", "NR",
      "4", "Relationship", "R",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("x").setValidator(ISSNValidator.getInstance());
    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value");

    getSubfield("b")
      .setMqTag("editionStatement");

    getSubfield("d")
      .setMqTag("provisionActivityStatement");

    getSubfield("e")
      .setMqTag("language");

    getSubfield("i")
      .setMqTag("relation");

    getSubfield("l")
      .setMqTag("dataProvenance");

    getSubfield("n")
      .setMqTag("note");

    getSubfield("s")
      .setMqTag("uniformTitle");

    getSubfield("t")
      .setBibframeTag("title");

    getSubfield("w")
      .setMqTag("recordControlNumber");

    getSubfield("x")
      .setBibframeTag("issn");

    getSubfield("4")
      .setMqTag("relationship")
      .setCodeList(RelatorCodes.getInstance());

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies");

    getSubfield("6")
      .setBibframeTag("linkage");

    getSubfield("8")
      .setMqTag("fieldLink");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
