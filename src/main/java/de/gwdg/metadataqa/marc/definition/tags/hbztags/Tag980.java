package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Selektionskennzeichen IZ
 */
public class Tag980 extends DataFieldDefinition {

  private static Tag980 uniqueInstance;

  private Tag980() {
    initialize();
    postCreation();
  }

  public static Tag980 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag980();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "980";
    label = "Selektionskennzeichen IZ";
    mqTag = "SelektionskennzeichenIZ";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";

    ind1 = new Indicator("undefined")
    .setCodes(  
      "1", "...",
      "2", "..."
    )
    .setMqTag("undefined");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Abrufzeichen", "R",
      "b", "Abrufzeichen, FBB", "R",
      "c", "Abrufzeichen, Bearb.", "R",
      "d", "Altdaten", "R",
      "e", "Abrufzeichen, Herkunft", "R",
      "r", "Storniert", "R",
      "t", "P2E-Prozess", "NR",
      "f", "Abrufzeichen Erwerbungsgruppe", "R",
      "g", "Abrufzeichen Publikationsarten", "R",
      "h", "Abrufzeichen Schenkungen,Leihgaben", "R",
      "s", "Sammlungsvermerke", "R",
      "x", "LKR", "R",
      "9", "Verweis auf Lokalinformation","NR"    
    );


  }
}
