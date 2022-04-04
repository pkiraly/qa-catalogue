package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Local Note on Additional Physical Form
 */
public class Tag930 extends DataFieldDefinition {
  private static Tag930 uniqueInstance;

  private Tag930() {
    initialize();
    postCreation();
  }

  public static Tag930 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag930();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "930";
    label = "Local Note on Additional Physical Form";
    mqTag = "LocalNoteOnAdditionalPhysicalForm";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Metamorfoze", "NR",
      "b", "Master File Museum Objects", "NR",
      "c", "Master File Illustrated Materials", "NR"
    );

    getSubfield("a").setMqTag("Metamorfoze");
    getSubfield("b").setMqTag("MasterFileMuseumObjects");
    getSubfield("c").setMqTag("MasterFileIllustratedMaterials");
  }
}
