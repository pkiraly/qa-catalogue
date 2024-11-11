package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

/**
 * Accessibility Note
 * https://www.loc.gov/marc/bibliographic/bd532.html
 */
public class Tag532 extends DataFieldDefinition {

  private static Tag532 uniqueInstance;

  private Tag532() {
    initialize();
    postCreation();
  }

  public static Tag532 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag532();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "532";
    label = "Accessibility Note";
    mqTag = "AccessibilityNote";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd532.html";

    ind1 = new Indicator("Display constant controller")
      .setCodes(
        "0", "Accessibility technical details",
        "1", "Accessibility features",
        "2", "Accessibility deficiencies",
        "8", "No display constant generated"
      )
      .setMqTag("displayConstantController");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Summary of accessibility", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("a")
      .setMqTag("summary");

    getSubfield("3")
      .setMqTag("materialsSpecified");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setContentParser(LinkageParser.getInstance());

    getSubfield("8")
      .setMqTag("fieldLink");

    setHistoricalSubfields(
      "z", "Source of note information (AM, CF, VM, SE) [OBSOLETE, 1990]"
    );

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
