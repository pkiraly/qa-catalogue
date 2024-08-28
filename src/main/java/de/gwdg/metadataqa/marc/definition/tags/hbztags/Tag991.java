package de.gwdg.metadataqa.marc.definition.tags.hbztags;	

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;	
/**
 * Bemerkung intern
 */
public class Tag991 extends DataFieldDefinition {	
  private static Tag991 uniqueInstance;	
  private Tag991() {
    initialize();
    postCreation();
  }	
  public static Tag991 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag991();
    return uniqueInstance;
  }	
  private void initialize() {
    tag = "991";
    label = "Bemerkung intern";
    mqTag = "bemerkungintern";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";	
    
    ind1 = new Indicator();
    ind2 = new Indicator();	

    setSubfieldsWithCardinality(
      "a", "Bemerkung intern,  Ausleihe", "R",
      "b", "Bemerkung intern, Sondermaterial", "R"
      );	
  }
}
