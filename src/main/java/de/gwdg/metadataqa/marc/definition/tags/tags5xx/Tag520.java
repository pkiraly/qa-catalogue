package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.general.codelist.ContentAdviceClassificationSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementDisplay;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Summary, etc.
 * http://www.loc.gov/marc/bibliographic/bd520.html
 */
public class Tag520 extends DataFieldDefinition {

  private static Tag520 uniqueInstance;

  private Tag520() {
    initialize();
    postCreation();
  }

  public static Tag520 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag520();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "520";
    label = "Summary, etc.";
    bibframeTag = "Summary";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd520.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Display constant controller")
      .setCodes(
        " ", "Summary",
        "0", "Subject",
        "1", "Review",
        "2", "Scope and content",
        "3", "Abstract",
        "4", "Content advice",
        "8", "No display constant generated"
      )
      .putVersionSpecificCodes(MarcVersion.NKCR,Arrays.asList(
        new EncodedValue("9", "MKP annotation for the Union catalog")
      ))
      .setMqTag("displayConstant")
      .setFrbrFunctions(ManagementDisplay);

    ind2 = new Indicator()
      .putVersionSpecificCodes(MarcVersion.NKCR,Arrays.asList(
        new EncodedValue("9", "Annotation language specified in subfield $9")
      ));

    setSubfieldsWithCardinality(
      "a", "Summary, etc.", "NR",
      "b", "Expansion of summary note", "NR",
      "c", "Assigning source", "NR",
      "u", "Uniform Resource Identifier", "R",
      "2", "Source", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "7", "Data provenance", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(ContentAdviceClassificationSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySelect)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setMqTag("expansion")
      .setCompilanceLevels("O");

    getSubfield("c")
      .setMqTag("assigningSource")
      .setCompilanceLevels("O");

    getSubfield("u")
      .setMqTag("uri")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("2")
      .setMqTag("source")
      .setCompilanceLevels("O");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("7")
      .setMqTag("dataProvenance");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.NKCR, Arrays.asList(
      new SubfieldDefinition("9", "Annotation language code", "NR")
    ));

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));

    setHistoricalSubfields(
      "z", "Source of note information (BK, AM, CF, SE) [OBSOLETE, 1990]"
    );
  }
}
