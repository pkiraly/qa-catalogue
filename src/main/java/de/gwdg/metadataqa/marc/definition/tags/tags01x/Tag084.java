package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.ClassificationSchemeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.SchemaFromSubfield2;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Other Classificaton Number
 * http://www.loc.gov/marc/bibliographic/bd084.html
 */
public class Tag084 extends DataFieldDefinition {

  private static Tag084 uniqueInstance;

  private Tag084() {
    initialize();
    postCreation();
  }

  public static Tag084 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag084();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "084";
    label = "Other Classificaton Number";
    bibframeTag = "Classification";
    // mqTag = "OtherClassificatonNumber";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd084.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Classification number", "R",
      "b", "Item number", "NR",
      "q", "Assigning agency", "NR",
      "2", "Number source", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("q").setCodeList(OrganizationCodes.getInstance());
    getSubfield("2").setCodeList(ClassificationSchemeSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("classificationPortion")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain);
    getSubfield("b").setBibframeTag("itemPortion")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain);
    getSubfield("q").setBibframeTag("assigner");
    getSubfield("2").setBibframeTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    // 084a_Classification_classificationPortion_ss:%22670%22
    fieldIndexer = SchemaFromSubfield2.getInstance();
  }
}
