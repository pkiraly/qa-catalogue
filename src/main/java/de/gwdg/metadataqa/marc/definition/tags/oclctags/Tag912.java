package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

/**
 * Work identifier
 * no documentation only practice ;-)
 */
public class Tag912 extends DataFieldDefinition {

  private static Tag912 uniqueInstance;

  private Tag912() {
    initialize();
    postCreation();
  }

  public static Tag912 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag912();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "912";
    label = "Work identifier";
    mqTag = "WorkIdentifier";
    cardinality = Cardinality.Repeatable; // according to DNB

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "9", "OCLC work identifier", "NR"
    );

    getSubfield("9").setMqTag("rdf:value");

    putVersionSpecificSubfields(MarcVersion.DNB, Arrays.asList(
      new SubfieldDefinition("a", "Kennzeichnung", "NR")
    ));
  }
}
