package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * BSZ-Feld für Codierungen
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=698777686
 */
public class Tag935 extends DataFieldDefinition {

  private static Tag935 uniqueInstance;

  private Tag935() {
    initialize();
    postCreation();
  }

  public static Tag935 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag935();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "935";
    label = "BSZ-Feld für Codierungen";
    mqTag = "BSZ-FeldFürCodierungen";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=698777686";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Abrufzeichen vierstellig", "R",
      "b", "Datenträger", "R",
      "c", "Veröffentlichungsart und -inhalt", "R",
      "d", "Codes aus Fremddaten", "R",
      "e", "Bibliographische Zitate", "R",
      "m", "Explizit der Unterlage", "R",
      "n", "Normierter Explizit", "R"
    );

  }
}
