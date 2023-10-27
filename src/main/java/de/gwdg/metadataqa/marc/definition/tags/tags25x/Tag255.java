package de.gwdg.metadataqa.marc.definition.tags.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySearch;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseInterpret;

/**
 * Cartographic Mathematical Data
 * https://www.loc.gov/marc/bibliographic/bd255.html
 */
public class Tag255 extends DataFieldDefinition {
  private static Tag255 uniqueInstance;

  private Tag255() {
    initialize();
    postCreation();
  }

  public static Tag255 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag255();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "255";
    label = "Cartographic Mathematical Data";
    bibframeTag = "Cartographic";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd255.html";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Statement of scale", "NR",
      "b", "Statement of projection", "NR",
      "c", "Statement of coordinates", "NR",
      "d", "Statement of zone", "NR",
      "e", "Statement of equinox", "NR",
      "f", "Outer G-ring coordinate pairs", "NR",
      "g", "Exclusion G-ring coordinate pairs", "NR",
      "6", "Linkage", "NR",
      "7", "Data provenance", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("scale")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, UseInterpret)
      .setCompilanceLevels("M", "M");

    getSubfield("b")
      .setBibframeTag("projection")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, UseInterpret)
      .setCompilanceLevels("A", "A");

    getSubfield("c")
      .setBibframeTag("coordinates")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect)
      .setCompilanceLevels("A", "A");

    getSubfield("d")
      .setBibframeTag("ascensionAndDeclination")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect)
      .setCompilanceLevels("A");

    getSubfield("e")
      .setBibframeTag("equinox")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect)
      .setCompilanceLevels("A");

    getSubfield("f")
      .setBibframeTag("outerGRing")
      .setCompilanceLevels("A");

    getSubfield("g")
      .setBibframeTag("exclusionGRing")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect)
      .setCompilanceLevels("A");

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

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
