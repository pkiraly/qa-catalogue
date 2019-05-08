package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
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

    getSubfield("a").setMqTag("numberOfWork")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain);
    getSubfield("b").setMqTag("numberOfMovement")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain);
    getSubfield("c").setMqTag("numberOfExcerpt")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain);
    getSubfield("d").setMqTag("caption")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain);
    getSubfield("e").setMqTag("role");
    getSubfield("g").setMqTag("clef")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain);
    getSubfield("m").setMqTag("voiceOrInstrument")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect);
    getSubfield("n").setMqTag("keySignature")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("o").setMqTag("timeSignature")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("p").setMqTag("musicalNotation")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("q").setMqTag("generalNote");
    getSubfield("r").setMqTag("keyOrMode")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("s").setMqTag("codedValidity")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("t").setMqTag("incipit");
    getSubfield("u").setMqTag("uri")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain);
    getSubfield("y").setMqTag("linkText");
    getSubfield("z").setMqTag("publicNote")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("2").setBibframeTag("systemCode")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
