package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Geographic Coverage Note
 * http://www.loc.gov/marc/bibliographic/bd522.html
 */
public class Tag522 extends DataFieldDefinition {

  private static Tag522 uniqueInstance;

  private Tag522() {
    initialize();
    postCreation();
  }

  public static Tag522 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag522();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "522";
    label = "Geographic Coverage Note";
    bibframeTag = "GeographicCoverage";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd522.html";

    ind1 = new Indicator("Display constant controller")
      .setCodes(
        " ", "Geographic coverage",
        "8", "No display constant generated"
      )
      .setMqTag("displayConstant")
      .setFrbrFunctions(ManagementDisplay);

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Geographic coverage note", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySelect);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
  }
}
