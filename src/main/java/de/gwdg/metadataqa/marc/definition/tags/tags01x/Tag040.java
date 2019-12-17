package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.DescriptionConventionSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Cataloging Source
 * http://www.loc.gov/marc/bibliographic/bd040.html
 */
public class Tag040 extends DataFieldDefinition {

  private static Tag040 uniqueInstance;

  private Tag040() {
    initialize();
    postCreation();
  }

  public static Tag040 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag040();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "040";
    label = "Cataloging Source";
    bibframeTag = "AdminMetadata";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd040.html";
    setLevels("M", "M");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Original cataloging agency", "NR",
      "b", "Language of cataloging", "NR",
      "c", "Transcribing agency", "NR",
      "d", "Modifying agency", "R",
      "e", "Description conventions", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("b").setCodeList(LanguageCodes.getInstance());
    getSubfield("e").setCodeList(DescriptionConventionSourceCodes.getInstance());
    OrganizationCodes orgCodes = OrganizationCodes.getInstance();
    getSubfield("a").setCodeList(orgCodes);
    getSubfield("c").setCodeList(orgCodes);
    getSubfield("d").setCodeList(orgCodes);

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("catalogingAgency")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A", "A");

    getSubfield("b")
      .setMqTag("languageOfCataloging")
      .setFrbrFunctions(ManagementProcess)
      .setLevels("A", "A");

    getSubfield("c")
      .setMqTag("transcribingAgency")
      .setFrbrFunctions(ManagementProcess)
      .setLevels("M", "M");

    getSubfield("d")
      .setMqTag("modifyingAgency")
      .setFrbrFunctions(ManagementProcess)
      .setLevels("A", "A");

    getSubfield("e")
      .setMqTag("descriptionConventions")
      .setFrbrFunctions(ManagementProcess)
      .setLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A", "A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("O");
  }
}
