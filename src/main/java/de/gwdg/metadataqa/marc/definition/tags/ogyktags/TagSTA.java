package de.gwdg.metadataqa.marc.definition.tags.ogyktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Locally defined field in OGYK
 */
public class TagSTA extends DataFieldDefinition {

  private static TagSTA uniqueInstance;

  private TagSTA() {
    initialize();
    postCreation();
  }

  public static TagSTA getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagSTA();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "STA";
    label = "Feldolgozottsági állapot";
    mqTag = "FeldolgozottsagiAllapot";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://wiki.ogyk.hu/doku.php?id=ogyk:gyszo:katszab:konyv:sta";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Státusz", "NR",
      "d", "Dátum", "NR"
    );

    getSubfield("a").setCodes(
      "feld1", "katalogizálás, állományba vétel",
      "feld2", "ellenőrzés (bib.rekord, pld.rekord, ACQ)",
      "feld3", "tárgyszavazás",
      "feld4", "tárgyszavazás - ellenőrzés",
      "feld5", "feldolgozás kész"
    );

    getSubfield("a").setMqTag("Statusz");
    getSubfield("d").setMqTag("Datum");

  }
}
