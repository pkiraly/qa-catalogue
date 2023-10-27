package de.gwdg.metadataqa.marc.definition.tags.tags4xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISSNValidator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySearch;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementDisplay;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Series Statement
 * https://www.loc.gov/marc/bibliographic/bd490.html
 */
public class Tag490 extends DataFieldDefinition {

  private static Tag490 uniqueInstance;

  private Tag490() {
    initialize();
    postCreation();
  }

  public static Tag490 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag490();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "490";
    label = "Series Statement";
    mqTag = "SeriesStatement";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd490.html";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator("Series tracing policy")
      .setCodes(
        "0", "Series not traced",
        "1", "Series traced"
      )
      .setMqTag("seriesTracing")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay);

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Series statement", "R",
      "l", "Library of Congress call number", "NR",
      "v", "Volume/sequential designation", "R",
      "x", "International Standard Serial Number", "R",
      "y", "Incorrect ISSN", "R",
      "z", "Canceled ISSN", "R",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "7", "Data provenance", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());
    getSubfield("x").setValidator(ISSNValidator.getInstance());
    getSubfield("z").setValidator(ISSNValidator.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("M", "M");

    getSubfield("l")
      .setMqTag("lccn")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("v")
      .setMqTag("volume")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("x")
      .setMqTag("issn")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("y")
      .setMqTag("incorrectISSN");

    getSubfield("z")
      .setMqTag("canceledISSN");

      getSubfield("3")
      .setMqTag("materialsSpecified")
      .setCompilanceLevels("O");

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
