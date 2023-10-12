package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.AccessRestrictionTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementDisplay;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseRestrict;

/**
 * Terms Governing Use and Reproduction Note
 * http://www.loc.gov/marc/bibliographic/bd540.html
 */
public class Tag540 extends DataFieldDefinition {

  private static Tag540 uniqueInstance;

  private Tag540() {
    initialize();
    postCreation();
  }

  public static Tag540 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag540();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "540";
    label = "Terms Governing Use and Reproduction Note";
    bibframeTag = "UsePolicy";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd540.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Terms governing use and reproduction", "NR",
      "b", "Jurisdiction", "NR",
      "c", "Authorization", "NR",
      "d", "Authorized users", "NR",
      "f", "Use and reproduction rights", "R",
      "g", "Availability date", "R",
      "q", "Supplying agency", "NR",
      "u", "Uniform Resource Identifier", "R",
      "2", "Source of term", "NR",
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(AccessRestrictionTermSourceCodes.getInstance());
    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(UseRestrict)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setMqTag("jurisdiction")
      .setFrbrFunctions(UseRestrict)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setBibframeTag("source")
      .setFrbrFunctions(UseRestrict)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("authorizedUsers")
      .setFrbrFunctions(UseRestrict)
      .setCompilanceLevels("A");

    getSubfield("f")
      .setMqTag("rights");

    getSubfield("g")
      .setMqTag("availabilityDate");

    getSubfield("q")
      .setMqTag("supplyingAgency");

    getSubfield("u")
      .setBibframeTag("rdfs:label").setMqTag("uri")
      .setFrbrFunctions(DiscoverySelect)
      .setCompilanceLevels("O");

    getSubfield("2")
      .setMqTag("source");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay)
      .setCompilanceLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
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
  }
}
