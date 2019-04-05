package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Methodology Note
 * http://www.loc.gov/marc/bibliographic/bd567.html
 */
public class Tag567 extends DataFieldDefinition {

  private static Tag567 uniqueInstance;

  private Tag567() {
    initialize();
    postCreation();
  }

  public static Tag567 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag567();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "567";
    label = "Methodology Note";
    mqTag = "Methodology";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd567.html";

    ind1 = new Indicator("Display constant controller")
      .setCodes(
        " ", "Methodology",
        "8", "No display constant generated"
      )
      .setMqTag("displayConstant")
      .setFrbrFunctions(ManagementDisplay);

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Methodology note", "NR",
      "b", "Controlled term", "R",
      "0", "Authority record control number or standard number", "R",
      "2", "Source of term", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setMqTag("rdf:value")
      .setFrbrFunctions(UseInterpret);
    getSubfield("b").setMqTag("controlledTerm");
    getSubfield("0").setMqTag("authorityRecordControlNumber");
    getSubfield("2").setMqTag("source");
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
  }
}
