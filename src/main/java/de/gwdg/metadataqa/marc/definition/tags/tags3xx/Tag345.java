package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Projection Characteristics of Moving Image
 * http://www.loc.gov/marc/bibliographic/bd345.html
 */
public class Tag345 extends DataFieldDefinition {
  private static Tag345 uniqueInstance;

  private Tag345() {
    initialize();
    postCreation();
  }

  public static Tag345 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag345();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "345";
    label = "Projection Characteristics of Moving Image";
    bibframeTag = "ProjectionCharacteristic";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd345.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Presentation format", "R",
      "b", "Projection speed", "R",
      "0", "Authority record control number or standard number", "R",
      "2", "Source", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("presentationFormat");

    getSubfield("b")
      .setBibframeTag("projectionSpeed");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber");

    getSubfield("2")
      .setBibframeTag("source");

    getSubfield("3")
      .setMqTag("materialsSpecified");

    getSubfield("6")
      .setBibframeTag("linkage");

    getSubfield("8")
      .setMqTag("fieldLink");
  }
}
