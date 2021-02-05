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
 * Metadata Provenance
 * http://www.loc.gov/marc/bibliographic/bd883.html
 */
public class Tag883 extends DataFieldDefinition {

  private static Tag883 uniqueInstance;

  private Tag883() {
    initialize();
    postCreation();
  }

  public static Tag883 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag883();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "883";
    label = "Metadata Provenance";
    mqTag = "MetadataProvenance";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd883.html";

    ind1 = new Indicator("Method of assignment")
      .setCodes(
        " ", "No information provided/not applicable",
        "0", "Fully machine-generated",
        "1", "Partially machine-generated",
        "2", "Not machine-generated"
      )
      .setMqTag("methodOfAssignment");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Creation process", "NR",
      "c", "Confidence value", "NR",
      "d", "Creation date", "NR",
      "q", "Assigning or generation agency", "NR",
      "x", "Validity end date", "NR",
      "u", "Uniform Resource Identifier", "NR",
      "w", "Bibliographic record control number", "R",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("a").setMqTag("creationProcess");

    getSubfield("c").setMqTag("confidence");

    getSubfield("d").setMqTag("date");

    getSubfield("q")
      .setMqTag("agency")
      .setCodeList(OrganizationCodes.getInstance());

    getSubfield("x").setMqTag("validityEndDate");

    getSubfield("u").setMqTag("uri");

    getSubfield("w").setMqTag("bibliographicRecordControlNumber");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("8").setMqTag("fieldLink");


    putVersionSpecificSubfields(MarcVersion.NKCR, Arrays.asList(
      new SubfieldDefinition("7", "NKCR Authority ID", "NR")
    ));

  }
}
