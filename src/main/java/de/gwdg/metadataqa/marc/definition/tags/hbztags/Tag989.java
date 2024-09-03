package de.gwdg.metadataqa.marc.definition.tags.hbztags;	

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;	
/**
 * Bemerkung extern
 */
public class Tag989 extends DataFieldDefinition {	
  private static Tag989 uniqueInstance;	
  private Tag989() {
    initialize();
    postCreation();
  }	
  public static Tag989 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag989();
    return uniqueInstance;
  }	
  private void initialize() {
    tag = "989";
    label = "Bemerkung extern";
    mqTag = "BemerkungExtern";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";	
    
    ind1 = new Indicator();
    ind2 = new Indicator();	

    setSubfieldsWithCardinality(
      "a", "Bemerkung extern,  Annotation", "R",
      "p", "Bemerkung extern,NE Personenname", "R",
      "t", "Bemerkung extern, NE Titel", "R",
      "2", "Herkunft", "R"
      );	
  }
}
