package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Local Added Entry Museum Collections (Uncontrolled)
 */
public class Tag971 extends DataFieldDefinition {
  private static Tag971 uniqueInstance;

  private Tag971() {
    initialize();
    postCreation();
  }

  public static Tag971 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag971();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "971";
    label = "Local Added Entry Museum Collections (Uncontrolled)";
    mqTag = "LocalAddedEntryMuseumCollectionsUncontrolled";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Name creator", "NR",
      "b", "Name patron", "NR",
      "c", "Site (archeology)", "NR",
      "e", "Relator term", "NR"
    );

    getSubfield("a").setMqTag("NameCreator");
    getSubfield("b").setMqTag("NamePatron");
    getSubfield("c").setMqTag("Site");
    getSubfield("e").setMqTag("RelatorTerm");
  }
}
