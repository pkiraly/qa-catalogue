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
 * Digital File Characteristics
 * https://www.loc.gov/marc/bibliographic/bd347.html
 */
public class Tag347 extends DataFieldDefinition {
  private static Tag347 uniqueInstance;

  private Tag347() {
    initialize();
    postCreation();
  }

  public static Tag347 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag347();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "347";
    label = "Digital File Characteristics";
    bibframeTag = "DigitalCharacteristic";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd347.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "File type", "R",
      "b", "Encoding format", "R",
      "c", "File size", "R",
      "d", "Resolution", "R",
      "e", "Regional encoding", "R",
      "f", "Encoded bitrate", "R",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "2", "Source", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("fileType");

    getSubfield("b")
      .setBibframeTag("encodingFormat");

    getSubfield("c")
      .setBibframeTag("fileSize");

    getSubfield("d")
      .setBibframeTag("resolution");

    getSubfield("e")
      .setBibframeTag("regionalEncoding");

    getSubfield("f")
      .setBibframeTag("encodedBitrate");

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
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
