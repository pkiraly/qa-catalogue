package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Local Subject Access Fields - Special Collections
 */
public class Tag692 extends DataFieldDefinition {
  private static Tag692 uniqueInstance;

  private Tag692() {
    initialize();
    postCreation();
  }

  public static Tag692 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag692();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "692";
    label = "Local Subject Access Fields - Special Collections";
    mqTag = "LocalSubjectAccessFieldsSpecialCollections";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "General Subject (TBC)", "NR",
      "b", "History of the Book - names", "NR",
      "c", "History of the Book - places", "NR",
      "s", "Topical Term Manuscripts", "NR",
      "0", "PPN", "NR",
      "2", "Brinkman trefwoorden", "NR"
    );

    getSubfield("a").setMqTag("GeneralSubjectTBC");
    getSubfield("b").setMqTag("HistoryoftheBookNnames");
    getSubfield("c").setMqTag("HistoryoftheBookNplaces");
    getSubfield("s").setMqTag("TopicalTermManuscripts");
    getSubfield("0").setMqTag("PPN");
    getSubfield("2").setMqTag("BrinkmanTtrefwoorden");
  }
}
