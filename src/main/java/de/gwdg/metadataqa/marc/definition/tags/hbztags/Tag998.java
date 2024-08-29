package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Lokale URL
 */
public class Tag998 extends DataFieldDefinition {

  private static Tag998 uniqueInstance;

  private Tag998() {
    initialize();
    postCreation();
  }

  public static Tag998 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag998();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "998";
    label = "Lokale URL";
    mqTag = "LokaleURL";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";

    ind1 = new Indicator("undefined")
    .setCodes(  
      " ", "...",
      "4", "4"
    )
    .setMqTag("undefined");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "URL aus 856 (Titel)", "R",
      "z", "Access Note", "NR",
      "u", "Lokale URL", "NR"
    );


  }
}
