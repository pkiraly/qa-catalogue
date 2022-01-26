package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Organization and Arrangement of Materials
 * http://www.loc.gov/marc/bibliographic/bd351.html
 */
public class Tag351 extends DataFieldDefinition {
  private static Tag351 uniqueInstance;

  private Tag351() {
    initialize();
    postCreation();
  }

  public static Tag351 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag351();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "351";
    label = "Organization and Arrangement of Materials";
    bibframeTag = "Arrangement";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd351.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Organization", "R",
      "b", "Arrangement", "R",
      "c", "Hierarchical level", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("organization")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("b")
      .setBibframeTag("pattern")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setBibframeTag("hierarchicalLevel")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

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
