package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseRestrict;

/**
 * Security Classification Control
 * http://www.loc.gov/marc/bibliographic/bd355.html
 */
public class Tag355 extends DataFieldDefinition {
  private static Tag355 uniqueInstance;

  private Tag355() {
    initialize();
    postCreation();
  }

  public static Tag355 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag355();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "355";
    label = "Security Classification Control";
    mqTag = "SecurityClassificationControl";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd355.html";
    setCompilanceLevels("A");

    ind1 = new Indicator("Controlled element")
      .setCodes(
        "0", "Document",
        "1", "Title",
        "2", "Abstract",
        "3", "Contents note",
        "4", "Author",
        "5", "Record",
        "8", "None of the above"
      )
      .setMqTag("controlledElement")
      .setFrbrFunctions(DiscoveryIdentify);

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Security classification", "NR",
      "b", "Handling instructions", "R",
      "c", "External dissemination information", "R",
      "d", "Downgrading or declassification event", "NR",
      "e", "Classification system", "NR",
      "f", "Country of origin code", "NR",
      "g", "Downgrading date", "NR",
      "h", "Declassification date", "NR",
      "j", "Authorization", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("f").setCodeList(CountryCodes.getInstance());
    getSubfield("j").setCodeList(OrganizationCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(UseRestrict)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setMqTag("handlingInstructions")
      .setFrbrFunctions(UseRestrict)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("externalDissemination")
      .setFrbrFunctions(UseRestrict)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("downgradingEvent")
      .setFrbrFunctions(UseRestrict)
      .setCompilanceLevels("A");

    getSubfield("e")
      .setMqTag("classificationSystem")
      .setFrbrFunctions(UseRestrict)
      .setCompilanceLevels("A");

    getSubfield("f")
      .setMqTag("countryOfOrigin")
      .setFrbrFunctions(UseRestrict)
      .setCompilanceLevels("A");

    getSubfield("g")
      .setMqTag("downgradingDate")
      .setFrbrFunctions(UseRestrict)
      .setCompilanceLevels("A");

    getSubfield("h")
      .setMqTag("declassificationDate")
      .setFrbrFunctions(UseRestrict)
      .setCompilanceLevels("A");

    getSubfield("j")
      .setBibframeTag("authorization")
      .setFrbrFunctions(UseRestrict)
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
