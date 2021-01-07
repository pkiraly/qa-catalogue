package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectCategoryCodeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.SchemaFromInd2AndSubfield2;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Subject Category Code
 * http://www.loc.gov/marc/bibliographic/bd072.html
 */
public class Tag072 extends DataFieldDefinition {

  private static Tag072 uniqueInstance;

  private Tag072() {
    initialize();
    postCreation();
  }

  public static Tag072 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag072();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "072";
    label = "Subject Category Code";
    bibframeTag = "Subject";
    mqTag = "SubjectCategoryCode";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd072.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator("Code source")
      .setCodes(
        "0", "NAL subject category code list",
        "7", "Source specified in subfield $2"
      )
      .putVersionSpecificCodes(MarcVersion.NKCR,Arrays.asList(
        new Code("9", "ANL subject category code list")
      ))
      .setHistoricalCodes(
        " ", "undefined"
      )
      .setMqTag("codeSource")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    setSubfieldsWithCardinality(
      "a", "Subject category code", "NR",
      "x", "Subject category code subdivision", "R",
      "2", "Source", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(SubjectCategoryCodeSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoverySelect)
      .setCompilanceLevels("M");

    getSubfield("x")
      .setMqTag("subdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoverySelect)
      .setCompilanceLevels("A");

    getSubfield("2")
      .setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.NKCR, Arrays.asList(
      new SubfieldDefinition("9", "Subject category number", "NR")
    ));

    sourceSpecificationType = SourceSpecificationType.Indicator2AndSubfield2;
  }
}
