package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Angabe zum Text der Unterlage
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=525369446
 */
public class Tag290 extends DataFieldDefinition {

  private static Tag290 uniqueInstance;

  private Tag290() {
    initialize();
    postCreation();
  }

  public static Tag290 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag290();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "290";
    label = "Angabe zum Text der Unterlage";
    mqTag = "angabeZumTextDerUnterlage";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=525369446";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Incipit der Unterlage", "R",
      "b", "Einheitsincipit", "R",
      "c", "Ausreifung / Entstehungsstufe", "R"
    );
  }
}
