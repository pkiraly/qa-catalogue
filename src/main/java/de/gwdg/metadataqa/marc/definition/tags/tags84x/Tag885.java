package de.gwdg.metadataqa.marc.definition.tags.tags84x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

import java.util.Arrays;

/**
 * Matching Information
 * http://www.loc.gov/marc/bibliographic/bd885.html
 */
public class Tag885 extends DataFieldDefinition {

  private static Tag885 uniqueInstance;

  private Tag885() {
    initialize();
    postCreation();
  }

  public static Tag885 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag885();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "885";
    label = "Matching Information";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd885.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Matching information", "NR",
      "b", "Status of matching and its checking", "NR",
      "c", "Confidence value", "NR",
      "d", "Generation date", "NR",
      "w", "Record control number", "R",
      "x", "Nonpublic note", "R",
      "z", "Public note", "R",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "2", "Source", "NR",
      "5", "Institution to which field applies", "NR"
    );

    getSubfield("a").setMqTag("matchingInformation");
    getSubfield("b").setMqTag("status");
    getSubfield("c").setMqTag("confidence");
    getSubfield("d").setMqTag("date");
    getSubfield("w").setMqTag("controlNumber");
    getSubfield("x").setMqTag("nonpublicNote");
    getSubfield("z").setMqTag("publicNote");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setMqTag("source")
      .setCodeList(OrganizationCodes.getInstance());

    getSubfield("5").setMqTag("institution");

    putVersionSpecificSubfields(MarcVersion.NKCR, Arrays.asList(
      new SubfieldDefinition("7", "NKCR Authority ID", "NR")
    ));

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
