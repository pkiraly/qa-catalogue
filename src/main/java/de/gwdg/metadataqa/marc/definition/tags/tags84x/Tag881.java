package de.gwdg.metadataqa.marc.definition.tags.tags84x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Manifestation Statements
 * http://www.loc.gov/marc/bibliographic/bd881.html
 */
public class Tag881 extends DataFieldDefinition {

  private static Tag881 uniqueInstance;

  private Tag881() {
    initialize();
    postCreation();
  }

  public static Tag881 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag881();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "881";
    label = "Manifestation Statements";
    mqTag = "ManifestationStatements";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd881.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Manifestation statement, high-level/general", "R",
      "b", "Manifestation identifier statement", "R",
      "c", "Manifestation title and responsibility statement", "R",
      "d", "Manifestation edition statement", "R",
      "e", "Manifestation production statement", "R",
      "f", "Manifestation publication statement", "R",
      "g", "Manifestation distribution statement", "R",
      "h", "Manifestation manufacture statement", "R",
      "i", "Manifestation copyright statement", "R",
      "j", "Manifestation frequency statement", "R",
      "k", "Manifestation designation of sequence statement", "R",
      "l", "Manifestation series statement", "R",
      "m", "Manifestation dissertation statement", "R",
      "n", "Manifestation regional encoding statement", "R",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("a").setMqTag("statement");
    getSubfield("b").setMqTag("identifier");
    getSubfield("c").setMqTag("titleAndResponsibility");
    getSubfield("d").setMqTag("edition");
    getSubfield("e").setMqTag("production");
    getSubfield("f").setMqTag("publication");
    getSubfield("g").setMqTag("distribution");
    getSubfield("h").setMqTag("manufacture");
    getSubfield("i").setMqTag("copyright");
    getSubfield("j").setMqTag("frequency");
    getSubfield("k").setMqTag("designation");
    getSubfield("l").setMqTag("series");
    getSubfield("m").setMqTag("dissertation");
    getSubfield("n").setMqTag("regionalEncoding");

    getSubfield("3").setMqTag("materialsSpecified");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setContentParser(LinkageParser.getInstance());

    getSubfield("8")
      .setMqTag("fieldLink");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
