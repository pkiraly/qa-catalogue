package de.gwdg.metadataqa.marc.definition.tags.hbztags;	

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;	
/**
 * IDX
 */
public class Tag996 extends DataFieldDefinition {	
  private static Tag996 uniqueInstance;	
  private Tag996() {
    initialize();
    postCreation();
  }	
  public static Tag996 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag996();
    return uniqueInstance;
  }	
  private void initialize() {
    tag = "996";
    label = "IDX";
    mqTag = "IDX";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";	
    
    ind1 = new Indicator();
    ind2 = new Indicator();	

    setSubfieldsWithCardinality(
      "a", "a", "R",
      "b", "b", "R",
      "c", "c", "R",
      "d", "d", "R",
      "e", "e", "R",
      "f", "f", "R",
      "g", "g", "R",
      "h", "h", "R",
      "k", "k", "R",
      "l", "l", "R",
      "m", "m", "R",
      "n", "n", "R",
      "o", "o", "R",
      "p", "p", "R",
      "r", "r", "R",
      "s", "s", "R",
      "t", "t", "R",
      "u", "u", "R",
      "v", "v", "R",
      "x", "x", "R",
      "z", "z", "R",
      "H", "H", "R",
      "0", "0", "R"

      );	
  }
}
