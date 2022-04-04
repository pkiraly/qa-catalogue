package de.gwdg.metadataqa.marc.definition.tags.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Publication, Distribution, etc. (Imprint)
 * http://www.loc.gov/marc/bibliographic/bd260.html
 */
public class Tag260 extends DataFieldDefinition {

  private static Tag260 uniqueInstance;

  private Tag260() {
    initialize();
    postCreation();
  }

  public static Tag260 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag260();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "260";
    label = "Publication, Distribution, etc. (Imprint)";
    bibframeTag = "Publication";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd260.html";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator("Sequence of publishing statements")
      .setCodes(
        " ", "Not applicable/No information provided/Earliest available publisher",
        "2", "Intervening publisher",
        "3", "Current/latest publisher"
      )
      .setHistoricalCodes(
        "0", "Publisher, distributor, etc. is present",
        "1", "Publisher, distributor, etc. not present"
      )
      .setMqTag("sequenceOfPublishingStatements")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain);

    ind2 = new Indicator()
      .setHistoricalCodes(
        "0", "Publisher, distributor, etc. not same as issuing body in added entry",
        "1", "Publisher, distributor, etc. same as issuing body in added entry"
      );

    setSubfieldsWithCardinality(
      "a", "Place of publication, distribution, etc.", "R",
      "b", "Name of publisher, distributor, etc.", "R",
      "c", "Date of publication, distribution, etc.", "R",
      "e", "Place of manufacture", "R",
      "f", "Manufacturer", "R",
      "g", "Date of manufacture", "R",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("place")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("b")
      .setBibframeTag("agent")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("c")
      .setBibframeTag("date")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("e")
      .setMqTag("placeOfManufacture")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("f")
      .setMqTag("manufacturer")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("g")
      .setMqTag("dateOfManufacture")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setCompilanceLevels("O");

    getSubfield("6")
      .setMqTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));

    setHistoricalSubfields(
      "d", "Plate or publisher's number for music (Pre-AACR2) [OBSOLETE, 1981] [CAN/MARC only] / Plate or publisher's number for music (Pre-AACR2) [OBSOLETE, 1999] [USMARC only]",
      "k", "Identification/manufacturer number [OBSOLETE, 1988] [CAN/MARC only]",
      "l", "Matrix and/or take number [OBSOLETE, 1988] [CAN/MARC only]"
    );
  }
}
