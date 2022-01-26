package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

import java.util.Arrays;

/**
 * Moving Image Characteristics
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
    label = "Moving Image Characteristics";
    mqTag = "MovingImageCharacteristics";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd345.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Presentation format", "R",
      "b", "Projection speed", "R",
      "c", "Aspect ratio value", "R",
      "d", "Aspect ratio designator", "R",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
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

    getSubfield("c")
      .setBibframeTag("aspectRatioValue");

    getSubfield("d")
      .setBibframeTag("aspectRatioDesignator");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setBibframeTag("source");

    getSubfield("3")
      .setMqTag("materialsSpecified");

    getSubfield("6")
      .setBibframeTag("linkage");

    getSubfield("8")
      .setMqTag("fieldLink");

    putVersionSpecificSubfields(MarcVersion.NKCR, Arrays.asList(
      new SubfieldDefinition("7", "NKCR Authority ID", "NR")
    ));

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR"),
      new SubfieldDefinition("@", "Language of field", "NR"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR")
    ));
  }
}
