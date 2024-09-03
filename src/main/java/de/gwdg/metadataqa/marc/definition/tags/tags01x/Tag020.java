package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISBNValidator;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySearch;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * International Standard Book Number
 * https://www.loc.gov/marc/bibliographic/bd020.html
 */
public class Tag020 extends DataFieldDefinition {

  private static Tag020 uniqueInstance;

  private Tag020() {
    initialize();
    postCreation();
  }

  public static Tag020 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag020();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "020";
    label = "International Standard Book Number";
    bibframeTag = "Isbn";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd020.html";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "International Standard Book Number", "NR",
      "c", "Terms of availability", "NR",
      "q", "Qualifying information", "R",
      "z", "Canceled/invalid ISBN", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );
    // TODO validation ISO 2108

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdf:value")
      .setValidator(ISBNValidator.getInstance())
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("c")
      .setBibframeTag("acquisitionTerms")
      .setFrbrFunctions(DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("q")
      .setBibframeTag("qualifier");

    getSubfield("z")
      .setMqTag("canceledOrInvalidISBN")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("6")
      .setMqTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A", "A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    setHistoricalSubfields(
      "b", "Binding information (BK, MP, MU) [OBSOLETE]"
    );

    putVersionSpecificSubfields(MarcVersion.DNB, Arrays.asList(
      new SubfieldDefinition("9", "ISBN mit Bindestrichen", "R")
    ));

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));

    putVersionSpecificSubfields(MarcVersion.HBZ, Arrays.asList(
      new SubfieldDefinition("9", "ISBN mit Bindestrichen (fakultativ)", "NR")
    ));
  }
}
