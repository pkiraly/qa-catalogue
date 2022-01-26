package de.gwdg.metadataqa.marc.definition.tags.tags84x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

/**
 * Replacement Record Information
 * http://www.loc.gov/marc/bibliographic/bd882.html
 */
public class Tag882 extends DataFieldDefinition {

  private static Tag882 uniqueInstance;

  private Tag882() {
    initialize();
    postCreation();
  }

  public static Tag882 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag882();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "882";
    label = "Replacement Record Information";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd882.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Replacement title", "R",
      "i", "Explanatory text", "R",
      "w", "Replacement bibliographic record control number", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("w").setCodeList(OrganizationCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("replacementTitle")
      .setCompilanceLevels("M");

    getSubfield("i")
      .setMqTag("explanatoryText")
      .setCompilanceLevels("O");

    getSubfield("w")
      .setMqTag("controlNumber")
      .setCompilanceLevels("A");

    getSubfield("6")
      .setMqTag("linkage")
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR"),
      new SubfieldDefinition("@", "Language of field", "NR"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR")
    ));
  }
}
