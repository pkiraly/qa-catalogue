package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Standard Technical Report Number
 * http://www.loc.gov/marc/bibliographic/bd027.html
 */
public class Tag027 extends DataFieldDefinition {

  private static Tag027 uniqueInstance;

  private Tag027() {
    initialize();
    postCreation();
  }

  public static Tag027 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag027();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "027";
    label = "Standard Technical Report Number";
    bibframeTag = "Strn";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd027.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Standard technical report number", "NR",
      "q", "Qualifying information", "R",
      "z", "Canceled/invalid number", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain);
    getSubfield("q").setMqTag("qualifyer");
    getSubfield("z").setMqTag("canceled")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
  }
}
