package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

import java.util.Arrays;

/**
 * Creator/Contributor Characteristics
 * http://www.loc.gov/marc/bibliographic/bd386.html
 */
public class Tag386 extends DataFieldDefinition {

  private static Tag386 uniqueInstance;

  private Tag386() {
    initialize();
    postCreation();
  }

  public static Tag386 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag386();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "386";
    label = "Creator/Contributor Characteristics";
    bibframeTag = "CreatorCharacteristic";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd386.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Creator/contributor term", "R",
      "b", "Creator/contributor code", "R",
      "i", "Relationship information", "R",
      "m", "Demographic group term", "NR",
      "n", "Demographic group code", "NR",
      "0", "Authority record control number or standard number", "R",
      "2", "Source", "NR",
      "3", "Materials specified", "NR",
      "4", "Relationship", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value");

    getSubfield("b")
      .setMqTag("code");

    getSubfield("i")
      .setMqTag("relationshipInformation");

    getSubfield("m")
      .setMqTag("demographicGroupTerm");

    getSubfield("n")
      .setMqTag("demographicGroupCode");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("2")
      .setMqTag("source");

    getSubfield("3")
      .setMqTag("materialsSpecified");

    getSubfield("4")
      .setMqTag("relationship");

    getSubfield("6")
      .setBibframeTag("linkage");

    getSubfield("8")
      .setMqTag("fieldLink");

    putVersionSpecificSubfields(MarcVersion.NKCR, Arrays.asList(
      new SubfieldDefinition("7", "NKCR Authority ID", "NR")
    ));
  }
}
