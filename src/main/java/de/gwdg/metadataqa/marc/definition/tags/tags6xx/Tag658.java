package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;
import de.gwdg.metadataqa.marc.definition.general.codelist.CurriculumObjectiveTermAndCodeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.SchemaFromSubfield2;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
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

    getSubfield("a").setMqTag("main")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("b").setMqTag("subordinate")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("c").setMqTag("code")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("d").setMqTag("correlationFactor");
    getSubfield("2").setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    fieldIndexer = SchemaFromSubfield2.getInstance();
    sourceSpecificationType = SourceSpecificationType.Subfield2;
  }
}
