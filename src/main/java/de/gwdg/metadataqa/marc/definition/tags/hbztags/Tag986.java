package de.gwdg.metadataqa.marc.definition.tags.hbztags;	

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;	
/**
 * Provenienzen: Einband
 */
public class Tag986 extends DataFieldDefinition {	
  private static Tag986 uniqueInstance;	
  private Tag986() {
    initialize();
    postCreation();
  }	
  public static Tag986 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag986();
    return uniqueInstance;
  }	
  private void initialize() {
    tag = "986";
    label = "Provenienzen: Einband";
    mqTag = "provenienzenEinband";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";	
    
    ind1 = new Indicator();
    ind2 = new Indicator();	

    setSubfieldsWithCardinality(
      "a", "Einbandtyp", "R",
      "b", "Material", "R",
      "c", "Abmessung", "R",
      "d", "Motive", "R",
      "e", "EBDB-ID der Motive", "R",
      "f", "Werkstatt", "R",
      "g", "EBDB-ID der Werkstatt", "R",
      "h", "Monogramm", "R",
      "i", "Herstellungsjahr", "R",
      "j", "Entstehungsort", "R",
      "k", "Referenznummern/Nachweis", "R",
      "l", "	Bemerkungen    ", "R",
      "8", "Feldverknüpfung und Reihenfolge", "NR"
      );	
  }
}
