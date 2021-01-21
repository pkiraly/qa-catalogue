package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Source of Description Note
 * http://www.loc.gov/marc/bibliographic/bd588.html
 */
public class Tag588 extends DataFieldDefinition {

  private static Tag588 uniqueInstance;

  private Tag588() {
    initialize();
    postCreation();
  }

  public static Tag588 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag588();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "588";
    label = "Source of Description Note";
    mqTag = "SourceOfDescription";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd588.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Display constant controller")
      .setCodes(
        " ", "No information provided",
        "0", "Source of description",
        "1", "Latest issue consulted"
      )
      .setMqTag("displayConstant");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Source of description note", "NR",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setCompilanceLevels("M");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies")
      .setCompilanceLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setCompilanceLevels("O");
  }
}
