package de.gwdg.metadataqa.marc.definition.tags.tags20x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Key Title
 * http://www.loc.gov/marc/bibliographic/bd222.html
 */
public class Tag222 extends DataFieldDefinition {
  private static Tag222 uniqueInstance;

  private Tag222() {
    initialize();
    postCreation();
  }

  public static Tag222 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag222();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "222";
    label = "Key Title";
    bibframeTag = "KeyTitle";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd222.html";
    setCompilanceLevels("A");

    ind1 = new Indicator();

    ind2 = new Indicator("Nonfiling characters")
      .setCodes(
        "0", "No nonfiling characters",
        "1-9", "Number of nonfiling characters"
      )
      .setHistoricalCodes(
        "0", "Key title is same as field 245 / No key title added entry; title proper same",
        "1", "Key title is not the same as field 245 / Key title added entry; title proper different",
        "2", "Key title added entry; title proper same",
        "3", "No key title added entry; title proper different"
      )
      .setMqTag("nonfilingCharacters")
      .setFrbrFunctions(ManagementProcess, ManagementSort);
    ind2.getCode("1-9").setRange(true);

    setSubfieldsWithCardinality(
      "a", "Key title", "NR",
      "b", "Qualifying information", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("mainTitle")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setBibframeTag("qualifier")
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
      new SubfieldDefinition("*", "Link with identifier", "NR"),
      new SubfieldDefinition("@", "Language of field", "NR"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR")
    ));
  }
}
