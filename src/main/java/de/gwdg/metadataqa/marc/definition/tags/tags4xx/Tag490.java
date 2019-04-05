package de.gwdg.metadataqa.marc.definition.tags.tags4xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISSNValidator;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Series Statement
 * http://www.loc.gov/marc/bibliographic/bd490.html
 */
public class Tag490 extends DataFieldDefinition {

  private static Tag490 uniqueInstance;

  private Tag490() {
    initialize();
    postCreation();
  }

  public static Tag490 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag490();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "490";
    label = "Series Statement";
    mqTag = "SeriesStatement";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd490.html";

    ind1 = new Indicator("Series tracing policy")
      .setCodes(
        "0", "Series not traced",
        "1", "Series traced"
      )
      .setMqTag("seriesTracing")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay);

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Series statement", "R",
      "l", "Library of Congress call number", "NR",
      "v", "Volume/sequential designation", "R",
      "x", "International Standard Serial Number", "R",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());
    getSubfield("x").setValidator(ISSNValidator.getInstance());

    getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
    getSubfield("l").setMqTag("lccn")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain);
    getSubfield("v").setMqTag("volume")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
    getSubfield("x").setMqTag("issn")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
    getSubfield("3").setMqTag("materialsSpecified");
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
  }
}
