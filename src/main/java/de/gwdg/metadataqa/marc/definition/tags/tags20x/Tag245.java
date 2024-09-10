package de.gwdg.metadataqa.marc.definition.tags.tags20x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySearch;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementDisplay;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementSort;

/**
 * Title Statement
 * https://www.loc.gov/marc/bibliographic/bd245.html
 */
public class Tag245 extends DataFieldDefinition {
  private static Tag245 uniqueInstance;

  private Tag245() {
    initialize();
    postCreation();
  }

  public static Tag245 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag245();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "245";
    label = "Title Statement";
    bibframeTag = "Title";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd245.html";
    setCompilanceLevels("M", "M");

    ind1 = new Indicator("Title added entry")
      .setCodes(
        "0", "No added entry",
        "1", "Added entry"
      )
      .setMqTag("titleAddedEntry")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay);

    ind2 = new Indicator("Nonfiling characters")
      .setCodes(
        "0", "No nonfiling characters",
        "1-9", "Number of nonfiling characters"
      )
      .setMqTag("nonfilingCharacters")
      .setFrbrFunctions(ManagementProcess, ManagementSort);
    ind2.getCode("1-9").setRange(true);

    setSubfieldsWithCardinality(
      "a", "Title", "NR",
      "b", "Remainder of title", "NR",
      "c", "Statement of responsibility, etc.", "NR",
      "f", "Inclusive dates", "NR",
      "g", "Bulk dates", "NR",
      "h", "Medium", "NR",
      "k", "Form", "R",
      "n", "Number of part/section of a work", "R",
      "p", "Name of part/section of a work", "R",
      "s", "Version", "NR",
      "6", "Linkage", "NR",
      "7", "Data provenance", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("mainTitle")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("M", "M");

    getSubfield("b")
      .setBibframeTag("subtitle")
      .setCompilanceLevels("A");

    getSubfield("c")
      .setBibframeTag("responsibilityStatement")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("f")
      .setBibframeTag("originDate").setMqTag("inclusiveDates")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("g")
      .setBibframeTag("originDate").setMqTag("bulkDates")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("h")
      .setBibframeTag("genreForm").setMqTag("medium")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("k")
      .setMqTag("form")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("n")
      .setBibframeTag("partNumber")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("p")
      .setBibframeTag("partName")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("s")
      .setMqTag("version")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A", "A");

    getSubfield("7")
      .setMqTag("dataProvenance");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    setHistoricalSubfields(
      "d", "Designation of section/part/series (SE) [OBSOLETE, 1979]",
      "e", "Name of part/section/series (SE) [OBSOLETE, 1979]"
    );

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));

    putVersionSpecificSubfields(MarcVersion.HBZ, Arrays.asList(
      new SubfieldDefinition("9", "Feldzuordnung Aleph", "R")
    ));
  }
}
