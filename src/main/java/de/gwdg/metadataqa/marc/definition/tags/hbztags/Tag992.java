package de.gwdg.metadataqa.marc.definition.tags.hbztags;	

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;	
/**
 * Signaturen
 */
public class Tag992 extends DataFieldDefinition {	
  private static Tag992 uniqueInstance;	
  private Tag992() {
    initialize();
    postCreation();
  }	
  public static Tag992 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag992();
    return uniqueInstance;
  }	
  private void initialize() {
    tag = "992";
    label = "Signaturen";
    mqTag = "Signaturen";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";	
    
    ind1 = new Indicator();
    ind2 = new Indicator();	

    setSubfieldsWithCardinality(
      "a", "Signaturen", "R",
      "b", "Herkunft", "NR"
      );	
  }
}
