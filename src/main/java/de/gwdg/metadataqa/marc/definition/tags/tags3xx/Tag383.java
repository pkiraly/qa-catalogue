package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.ThematicIndexCodeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

/**
 * Numeric Designation of Musical Work or Expression
 * https://www.loc.gov/marc/bibliographic/bd383.html
 */
public class Tag383 extends DataFieldDefinition {
  private static Tag383 uniqueInstance;

  private Tag383() {
    initialize();
    postCreation();
  }

  public static Tag383 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag383();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "383";
    label = "Numeric Designation of Musical Work or Expression";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd383.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Type of entity")
      .setCodes(
        " ", "No information provided",
        "0", "Work",
        "1", "Expression"
      )
      .setMqTag("typeOfEntity");
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Serial number", "R",
      "b", "Opus number", "R",
      "c", "Thematic index number", "R",
      "d", "Thematic index code", "NR",
      "e", "Publisher associated with opus number", "NR",
      "2", "Source", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "7", "Data provenance", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(ThematicIndexCodeSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    // TODO: set Bibframe tag

    getSubfield("a")
      .setMqTag("serialNumber")
      .setCompilanceLevels("A");

    getSubfield("b")
      .setMqTag("opusNumber")
      .setCompilanceLevels("M");

    getSubfield("c")
      .setMqTag("thematicIndexNumber")
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("thematicIndexCode");

    getSubfield("e")
      .setMqTag("publisher");

    getSubfield("2")
      .setMqTag("source");

    getSubfield("3")
      .setMqTag("materials");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setCompilanceLevels("A");

    getSubfield("7")
      .setMqTag("dataProvenance");

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
