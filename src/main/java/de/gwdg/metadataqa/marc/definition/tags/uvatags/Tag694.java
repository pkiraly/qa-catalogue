package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Local Subject Access Fields - Museum Collections
 */
public class Tag694 extends DataFieldDefinition {
  private static Tag694 uniqueInstance;

  private Tag694() {
    initialize();
    postCreation();
  }

  public static Tag694 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag694();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "694";
    label = "Local Subject Access Fields - Museum Collections";
    mqTag = "LocalSubjectAccessFieldsMuseumCollections";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Topical Term (category)", "NR",
      "b", "Topical Term (function)", "NR",
      "c", "Topical Term (style/period)", "NR",
      "d", "Topical Term (culture)", "NR",
      "e", "Topical Term (subculture)", "NR",
      "f", "Topical Term (country/place)", "NR",
      "g", "Topical Term (associated concepts)", "NR",
      "h", "Topical Term (phylum)", "NR",
      "i", "Topical Term (class)", "NR",
      "j", "Topical Term (order)", "NR",
      "k", "Topical Term (suborder)", "NR",
      "l", "Topical Term (superfamily)", "NR"
    );

    getSubfield("a").setMqTag("TopicalTermCategory");
    getSubfield("b").setMqTag("TopicalTermFunction");
    getSubfield("c").setMqTag("TopicalTermStylePeriod");
    getSubfield("d").setMqTag("TopicalTermCulture");
    getSubfield("e").setMqTag("TopicalTermSubculture");
    getSubfield("f").setMqTag("TopicalTermCountryPlace");
    getSubfield("g").setMqTag("TopicalTermAssociatedConcepts");
    getSubfield("h").setMqTag("TopicalTermPhylum");
    getSubfield("i").setMqTag("TopicalTermClass");
    getSubfield("j").setMqTag("TopicalTermOrder");
    getSubfield("k").setMqTag("TopicalTermSuborder");
    getSubfield("l").setMqTag("TopicalTermSuperfamily");
  }
}
