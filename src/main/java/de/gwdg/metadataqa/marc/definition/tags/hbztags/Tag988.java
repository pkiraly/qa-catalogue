package de.gwdg.metadataqa.marc.definition.tags.hbztags;	

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;	
/**
 * Digitalisierungsangaben
 */
public class Tag988 extends DataFieldDefinition {	
  private static Tag988 uniqueInstance;	
  private Tag988() {
    initialize();
    postCreation();
  }	
  public static Tag988 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag988();
    return uniqueInstance;
  }	
  private void initialize() {
    tag = "988";
    label = "Digitalisierungsangaben";
    mqTag = "Digitalisierungsangaben";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";	
    
    ind1 = new Indicator();
    ind2 = new Indicator();	

    setSubfieldsWithCardinality(
      "a", "Marginalien", "R",
      "b", "Extras Inhalt", "R",
      "c", "Allg. Anmerkung", "R",
      "u", "URL", "R",
      "z", "Anmerkung zur URL", "R"
      );	
  }
}
