package de.gwdg.metadataqa.marc.definition.tags.hbztags;	

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;	
/**
 * Aufnahmeinformation
 */
public class Tag994 extends DataFieldDefinition {	
  private static Tag994 uniqueInstance;	
  private Tag994() {
    initialize();
    postCreation();
  }	
  public static Tag994 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag994();
    return uniqueInstance;
  }	
  private void initialize() {
    tag = "994";
    label = "Aufnahmeinformation";
    mqTag = "Aufnahmeinformation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";	
    
    ind1 = new Indicator();
    ind2 = new Indicator();	

    setSubfieldsWithCardinality(
      "a", "Aufnahmeland", "R",
      "b", "Sendeanstalt", "NR",
      "c", "Sendedatum", "NR",
      "d", "Bandposition", "NR",
      "9", "Verweis auf Lokalinformation","NR"    
      );	
  }
}
