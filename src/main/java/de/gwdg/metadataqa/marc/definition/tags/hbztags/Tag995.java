package de.gwdg.metadataqa.marc.definition.tags.hbztags;	

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;	
/**
 * Bestellangaben
 */
public class Tag995 extends DataFieldDefinition {	
  private static Tag995 uniqueInstance;	
  private Tag995() {
    initialize();
    postCreation();
  }	
  public static Tag995 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag995();
    return uniqueInstance;
  }	
  private void initialize() {
    tag = "995";
    label = "Bestellangaben";
    mqTag = "Bestellangaben";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";	
    
    ind1 = new Indicator();
    ind2 = new Indicator();	

    setSubfieldsWithCardinality(
      "a", "Bestellangaben", "R",
      "b", "Storniert", "R",
      "c", "Bearbeitungsvermerk Dissertationen", "R",
      "d", "Standorte", "R",
      "2", "Herkunft", "NR"      
      );	
  }
}
