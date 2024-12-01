package de.gwdg.metadataqa.marc.definition.tags.ogyktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Locally defined field in OGYK
 */
public class Tag972 extends DataFieldDefinition {

  private static Tag972 uniqueInstance;

  private Tag972() {
    initialize();
    postCreation();
  }

  public static Tag972 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag972();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "972";
    label = "Gyűjteményi témakód (monografikus)";
    mqTag = "GyujtemenyiTemakodMonografikus";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://wiki.ogyk.hu/doku.php?id=ogyk:gyszo:katszab:konyv:972";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Témakód", "NR"
    );

    getSubfield("a").setMqTag("Temakod");

  }
}
