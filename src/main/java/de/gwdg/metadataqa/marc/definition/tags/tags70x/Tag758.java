package de.gwdg.metadataqa.marc.definition.tags.tags70x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;
import de.gwdg.metadataqa.marc.definition.general.codelist.NameAndTitleAuthoritySourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.TaxonomicClassificationSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Resource Identifier
 * https://www.loc.gov/marc/bibliographic/bd758.html
 */
public class Tag758 extends DataFieldDefinition {

  private static Tag758 uniqueInstance;

  private Tag758() {
    initialize();
    postCreation();
  }

  public static Tag758 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag758();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "758";
    label = "Resource Identifier";
    mqTag = "ResourceIdentifier";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd758.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Label", "NR",
      "i", "Relationship information", "R",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "2", "Source of heading or term", "NR",
      "3", "Materials specified", "NR",
      "4", "Relationship", "R",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(NameAndTitleAuthoritySourceCodes.getInstance());
    getSubfield("5").setCodeList(OrganizationCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("label");

    getSubfield("i")
      .setMqTag("Relationship");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber");

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setMqTag("source");

    getSubfield("3")
      .setMqTag("materialsSpecified");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies");

    getSubfield("6")
      .setBibframeTag("linkage");

    getSubfield("8")
      .setMqTag("fieldLink");

    putVersionSpecificSubfields(MarcVersion.NKCR, Arrays.asList(
      new SubfieldDefinition("7", "NKCR Authority ID", "NR"),
      new SubfieldDefinition("9", "NKCR Authority field - tracing form", "NR")
    ));

    sourceSpecificationType = SourceSpecificationType.Subfield2;
  }
}
