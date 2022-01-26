package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;
import de.gwdg.metadataqa.marc.definition.general.codelist.CurriculumObjectiveTermAndCodeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Index Term - Curriculum Objective
 * http://www.loc.gov/marc/bibliographic/bd658.html
 */
public class Tag658 extends DataFieldDefinition {

  private static Tag658 uniqueInstance;

  private Tag658() {
    initialize();
    postCreation();
  }

  public static Tag658 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag658();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "658";
    label = "Index Term - Curriculum Objective";
    mqTag = "CurriculumObjective";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd658.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Main curriculum objective", "NR",
      "b", "Subordinate curriculum objective", "R",
      "c", "Curriculum code", "NR",
      "d", "Correlation factor", "NR",
      "2", "Source of term or code", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(CurriculumObjectiveTermAndCodeSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("main")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setMqTag("subordinate")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("code")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("correlationFactor")
      .setCompilanceLevels("A");

    getSubfield("2")
      .setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("M");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.MARC21NO, Arrays.asList(
      new SubfieldDefinition("9", "Language code", "NR")
    ));

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR"),
      new SubfieldDefinition("@", "Language of field", "NR"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR")
    ));

    sourceSpecificationType = SourceSpecificationType.Subfield2;
  }
}
