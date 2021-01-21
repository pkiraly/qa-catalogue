package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.MusicalIncipitSchemeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Musical Incipits Information
 * http://www.loc.gov/marc/bibliographic/bd031.html
 */
public class Tag031 extends DataFieldDefinition {

  private static Tag031 uniqueInstance;

  private Tag031() {
    initialize();
    postCreation();
  }

  public static Tag031 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag031();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "031";
    label = "Musical Incipits Information";
    mqTag = "MusicalIncipits";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd031.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Number of work", "NR",
      "b", "Number of movement", "NR",
      "c", "Number of excerpt", "NR",
      "d", "Caption or heading", "R",
      "e", "Role", "NR",
      "g", "Clef", "NR",
      "m", "Voice/instrument", "NR",
      "n", "Key signature", "NR",
      "o", "Time signature", "NR",
      "p", "Musical notation", "NR",
      "q", "General note", "R",
      "r", "Key or mode", "NR",
      "s", "Coded validity note", "R",
      "t", "Text incipit", "R",
      "u", "Uniform Resource Identifier", "R",
      "y", "Link text", "R",
      "z", "Public note", "R",
      "2", "System code", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("s").setCodes(
      "?", "there is a mistake in the incipit that has not been corrected",
      "+", "there is a mistake in the incipit that has been corrected",
      "t", "the incipit has been transcribed (e.g. from mensural notation)",
      "!", "incipit discrepancies have been commented on in subfield $q (General note)."
    );

    getSubfield("2").setCodeList(MusicalIncipitSchemeSourceCodes.getInstance());
    getSubfield("6").setContentParser(LinkageParser.getInstance());

    // TODO: u - URL
    // TODO: n - Letter “x” indicates sharps and the letter “b” indicates flats followed by capital letters to indicate the affected pitches.

    getSubfield("a")
      .setMqTag("numberOfWork")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("b")
      .setMqTag("numberOfMovement")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("numberOfExcerpt")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("caption")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("e")
      .setMqTag("role")
      .setCompilanceLevels("O");

    getSubfield("g")
      .setMqTag("clef")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("m")
      .setMqTag("voiceOrInstrument")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect)
      .setCompilanceLevels("O");

    getSubfield("n")
      .setMqTag("keySignature")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("o")
      .setMqTag("timeSignature")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("p")
      .setMqTag("musicalNotation")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    getSubfield("q")
      .setMqTag("generalNote")
      .setCompilanceLevels("O");

    getSubfield("r")
      .setMqTag("keyOrMode")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("s")
      .setMqTag("codedValidity")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("t")
      .setMqTag("incipit")
      .setCompilanceLevels("A");

    getSubfield("u")
      .setMqTag("uri")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("y")
      .setMqTag("linkText")
      .setCompilanceLevels("O");

    getSubfield("z")
      .setMqTag("publicNote")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("2")
      .setBibframeTag("systemCode")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");
  }
}
