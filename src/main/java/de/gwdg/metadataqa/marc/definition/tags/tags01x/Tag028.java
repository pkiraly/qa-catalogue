package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Publisher or Distributor Number
 * http://www.loc.gov/marc/bibliographic/bd028.html
 */
public class Tag028 extends DataFieldDefinition {

  private static Tag028 uniqueInstance;

  private Tag028() {
    initialize();
    postCreation();
  }

  public static Tag028 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag028();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "028";
    label = "Publisher or Distributor Number";
    mqTag = "PublisherNumber";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd028.html";
    setLevels("A", "A");

    ind1 = new Indicator("Type of number")
      .setCodes(
        "0", "Issue number",
        "1", "Matrix number",
        "2", "Plate number",
        "3", "Other music publisher number",
        "4", "Video recording publisher number",
        "5", "Other publisher number",
        "6", "Distributor number"
      )
      .putVersionSpecificCodes(MarcVersion.SZTE, Arrays.asList(
        new Code(" ", "Not specified")
      ))
      .setMqTag("type")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess, ManagementDisplay);

    // TODO: overwrite getIndexTag to use these values:
    ind1.getCode("0").setBibframeTag("AudioIssueNumber");
    ind1.getCode("1").setBibframeTag("MatrixNumber");
    ind1.getCode("2").setBibframeTag("MusicPlate");
    ind1.getCode("3").setBibframeTag("MusicPublisherNumber");
    ind1.getCode("4").setBibframeTag("VideoRecordingNumber");
    ind1.getCode("5").setBibframeTag("PublisherNumber");

    ind2 = new Indicator("Note/added entry controller")
      .setCodes(
        "0", "No note, no added entry",
        "1", "Note, added entry",
        "2", "Note, no added entry",
        "3", "No note, added entry"
      )
      .setMqTag("noteController")
      .setFrbrFunctions(ManagementDisplay);

    setSubfieldsWithCardinality(
      "a", "Publisher or distributor number", "NR",
      "b", "Source", "NR",
      "q", "Qualifying information", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setLevels("M", "M");

    getSubfield("b")
      .setBibframeTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("M", "M");

    getSubfield("q")
      .setBibframeTag("qualifier");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A", "A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("O");
  }
}
