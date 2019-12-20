package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Character Sets Present
 * http://www.loc.gov/marc/bibliographic/bd066.html
 */
public class Tag066 extends DataFieldDefinition {

  private static Tag066 uniqueInstance;

  private Tag066() {
    initialize();
    postCreation();
  }

  public static Tag066 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag066();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "066";
    label = "Character Sets Present";
    mqTag = "CharacterSets";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd066.html";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Primary G0 character set", "NR",
      "b", "Primary G1 character set", "NR",
      "c", "Alternate G0 or G1 character set", "R"
    );

    getSubfield("a")
      .setMqTag("g0")
      .setFrbrFunctions(ManagementProcess)
      .setCompilanceLevels("O");

    getSubfield("b")
      .setMqTag("g1")
      .setFrbrFunctions(ManagementProcess)
      .setCompilanceLevels("O");

    getSubfield("c")
      .setMqTag("alternate")
      .setFrbrFunctions(ManagementProcess)
      .setCompilanceLevels("M");
  }
}
