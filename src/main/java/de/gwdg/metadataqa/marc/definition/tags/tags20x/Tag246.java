package de.gwdg.metadataqa.marc.definition.tags.tags20x;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Varying Form of Title
 * http://www.loc.gov/marc/bibliographic/bd246.html
 */
public class Tag246 extends DataFieldDefinition {
  private static Tag246 uniqueInstance;

  private Tag246() {
    initialize();
    postCreation();
  }

  public static Tag246 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag246();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "246";
    label = "Varying Form of Title";
    bibframeTag = "ParallelTitle";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd246.html";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator("Note/added entry controller")
      .setCodes(
        "0", "Note, no added entry",
        "1", "Note, added entry",
        "2", "No note, no added entry",
        "3", "No note, added entry"
      )
      .putVersionSpecificCodes(MarcVersion.SZTE, Arrays.asList(
        new EncodedValue(" ", "Not specified")
      ))
      .setMqTag("noteAndAddedEntry")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay);

    ind2 = new Indicator("Type of title")
      .setCodes(
        " ", "No type specified",
        "0", "Portion of title",
        "1", "Parallel title",
        "2", "Distinctive title",
        "3", "Other title",
        "4", "Cover title",
        "5", "Added title page title",
        "6", "Caption title",
        "7", "Running title",
        "8", "Spine title"
      )
      .putVersionSpecificCodes(MarcVersion.DNB, Arrays.asList(
        new EncodedValue("9", "Ansetzungstitel")
      ))
      .setMqTag("type")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess, ManagementSort);

    setSubfieldsWithCardinality(
      "a", "Title proper/short title", "NR",
      "b", "Remainder of title", "NR",
      "f", "Date or sequential designation", "NR",
      "g", "Miscellaneous information", "R",
      "h", "Medium", "NR",
      "i", "Display text", "NR",
      "n", "Number of part/section of a work", "R",
      "p", "Name of part/section of a work", "R",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("5").setCodeList(OrganizationCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("mainTitle")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("M", "M");

    getSubfield("b")
      .setBibframeTag("subtitle")
      .setCompilanceLevels("A");

    getSubfield("f")
      .setBibframeTag("date")
      .setCompilanceLevels("A", "A");

    getSubfield("g")
      .setBibframeTag("miscellaneous")
      .setCompilanceLevels("A");

    getSubfield("h")
      .setMqTag("medium")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("i")
      .setMqTag("displayText")
      .setCompilanceLevels("A");

    getSubfield("n")
      .setBibframeTag("partNumber")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("p")
      .setBibframeTag("partName")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay)
      .setCompilanceLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A", "A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    setHistoricalSubfields(
      "c", "Remainder of title page transcription [OBSOLETE, 1991] [CAN/MARC only]",
      "d", "Designation of section/part/series (SE) [OBSOLETE, 1979]",
      "e", "Name of section/part/series (SE) [OBSOLETE, 1979]"
    );
  }
}
