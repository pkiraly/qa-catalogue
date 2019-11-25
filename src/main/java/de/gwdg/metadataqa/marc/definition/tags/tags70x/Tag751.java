package de.gwdg.metadataqa.marc.definition.tags.tags70x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;
import de.gwdg.metadataqa.marc.definition.general.codelist.NameAndTitleAuthoritySourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;

/**
 * Added Entry - Geographic Name
 * http://www.loc.gov/marc/bibliographic/bd751.html
 */
public class Tag751 extends DataFieldDefinition {

  private static Tag751 uniqueInstance;

  private Tag751() {
    initialize();
    postCreation();
  }

  public static Tag751 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag751();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "751";
    label = "Added Entry - Geographic Name";
    mqTag = "AddedGeographicName";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd751.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Geographic name", "NR",
      "e", "Relator term", "R",
      "0", "Authority record control number or standard number", "R",
      "2", "Source of heading or term", "NR",
      "3", "Materials specified", "NR",
      "4", "Relationship", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(NameAndTitleAuthoritySourceCodes.getInstance());
    getSubfield("4").setCodeList(RelatorCodes.getInstance());

    getSubfield("0").setContentParser(RecordControlNumberParser.getInstance());
    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setMqTag("rdf:value");
    getSubfield("e").setMqTag("relator");
    getSubfield("0").setMqTag("authorityRecordControlNumber");
    getSubfield("2").setMqTag("source");
    getSubfield("3").setMqTag("materialsSpecified");
    getSubfield("4").setMqTag("relationship");
    getSubfield("6").setBibframeTag("linkage");
    getSubfield("8").setMqTag("fieldLink");

    sourceSpecificationType = SourceSpecificationType.Subfield2;
  }
}
