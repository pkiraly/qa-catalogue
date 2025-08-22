package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;
import de.gwdg.metadataqa.marc.definition.general.codelist.ClassificationSchemeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySearch;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Classification Numbers Assigned in Canada
 * https://www.loc.gov/marc/bibliographic/bd055.html
 */
public class Tag055 extends DataFieldDefinition {

  private static Tag055 uniqueInstance;

  private Tag055() {
    initialize();
    postCreation();
  }

  public static Tag055 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag055();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "055";
    label = "Classification Numbers Assigned in Canada";
    bibframeTag = "ClassificationLcc";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd055.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Existence in LAC collection")
      .setCodes(
        " ", "Information not provided",
        "0", "Work held by LAC",
        "1", "Work not held by LAC"
      )
      .setMqTag("heldByLAC")
      .setFrbrFunctions(DiscoverySelect, DiscoveryObtain);

    ind2 = new Indicator("Type, completeness, source of call/class number")
      .setCodes(
        "0", "LCC (or LCC-compatible) call number assigned by LAC",
        "1", "Complete LCC (or LCC-compatible) class number assigned by LAC",
        "2", "Incomplete LCC (or LCC-compatible) class number assigned by LAC",
        "3", "LCC (or LCC-compatible) call number assigned by a Canadian organization other than LAC",
        "4", "Complete LCC (or LCC-compatible) class number assigned by a Canadian organization other than LAC",
        "5", "Incomplete LCC (or LCC-compatible) class number assigned by a Canadian organization other than LAC",
        "6", "Other call number assigned by LAC",
        "7", "Other class number assigned by LAC",
        "8", "Other call number assigned by a Canadian organization other than LAC",
        "9", "Other class number assigned by a Canadian organization other than LAC"
      )
      .setMqTag("type")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    setSubfieldsWithCardinality(
      "a", "Classification number", "NR",
      "b", "Item number", "NR",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "2", "Source of call/class number", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(ClassificationSchemeSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("classificationPortion").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setBibframeTag("itemPortion")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.NKCR, Arrays.asList(
      new SubfieldDefinition("7", "NKCR Authority ID", "NR")
    ));

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));

    sourceSpecificationType = SourceSpecificationType.Indicator2For055AndSubfield2;
  }
}
