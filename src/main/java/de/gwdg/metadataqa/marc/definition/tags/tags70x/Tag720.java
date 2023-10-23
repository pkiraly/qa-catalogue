package de.gwdg.metadataqa.marc.definition.tags.tags70x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySearch;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Added Entry - Uncontrolled Name
 * http://www.loc.gov/marc/bibliographic/bd720.html
 */
public class Tag720 extends DataFieldDefinition {

  private static Tag720 uniqueInstance;

  private Tag720() {
    initialize();
    postCreation();
  }

  public static Tag720 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag720();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "720";
    label = "Added Entry - Uncontrolled Name";
    mqTag = "UncontrolledName";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd720.html";
    setCompilanceLevels("A");

    ind1 = new Indicator("Type of name")
      .setCodes(
        " ", "Not specified",
        "1", "Personal",
        "2", "Other"
      )
      .setMqTag("type")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Name", "NR",
      "e", "Relator term", "R",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "4", "Relationship", "R",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "7", "Data provenance", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("M");

    getSubfield("e")
      .setMqTag("relator")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("4")
      .setMqTag("relationship")
      .setCodeList(RelatorCodes.getInstance())
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("7")
      .setMqTag("dataProvenance");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
