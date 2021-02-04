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
 * Sound Characteristics
 * http://www.loc.gov/marc/bibliographic/bd344.html
 */
public class Tag344 extends DataFieldDefinition {
  private static Tag344 uniqueInstance;

  private Tag344() {
    initialize();
    postCreation();
  }

  public static Tag344 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag344();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "344";
    label = "Sound Characteristics";
    bibframeTag = "SoundCharacteristic";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd344.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Type of recording", "R",
      "b", "Recording medium", "R",
      "c", "Playing speed", "R",
      "d", "Groove characteristic", "R",
      "e", "Track configuration", "R",
      "f", "Tape configuration", "R",
      "g", "Configuration of playback channels", "R",
      "h", "Special playback characteristics", "R",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "2", "Source", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("recordingMethod");

    getSubfield("b")
      .setBibframeTag("recordingMedium");

    getSubfield("c")
      .setBibframeTag("playingSpeed");

    getSubfield("d")
      .setBibframeTag("grooveCharacteristics");

    getSubfield("e")
      .setBibframeTag("trackConfig");

    getSubfield("f")
      .setBibframeTag("tapeConfig");

    getSubfield("g")
      .setBibframeTag("playbackChannels");

    getSubfield("h")
      .setBibframeTag("playbackCharacteristic");

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
  }
}
