package de.gwdg.metadataqa.marc.definition.tags.tags84x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

import java.util.Arrays;

/**
 * Machine-generated Metadata Provenance
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
    label = "Machine-generated Metadata Provenance";
    mqTag = "MachineGeneratedMetadataProvenance";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd883.html";

    ind1 = new Indicator("Method of machine assignment")
      .setCodes(
        " ", "No information provided/not applicable",
        "0", "Fully machine-generated",
        "1", "Partially machine-generated"
      )
      .setMqTag("methodOfMachineAssignment");
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Generation process", "NR",
      "c", "Confidence value", "NR",
      "d", "Generation date", "NR",
      "q", "Generation agency", "NR",
      "x", "Validity end date", "NR",
      "u", "Uniform Resource Identifier", "NR",
      "w", "Bibliographic record control number", "R",
      "0", "Authority record control number or standard number", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("q").setCodeList(OrganizationCodes.getInstance());

    getSubfield("a").setMqTag("generationProcess");
    getSubfield("c").setMqTag("confidence");
    getSubfield("d").setMqTag("date");
    getSubfield("q").setMqTag("agency");
    getSubfield("x").setMqTag("validityEndDate");
    getSubfield("u").setMqTag("uri");
    getSubfield("w").setMqTag("bibliographicRecordControlNumber");
    getSubfield("0").setMqTag("authorityRecordControlNumber");
    getSubfield("8").setMqTag("fieldLink");

    putVersionSpecificSubfields(MarcVersion.NKCR, Arrays.asList(
      new SubfieldDefinition("7", "NKCR Authority ID", "NR")
    ));

  }
}
