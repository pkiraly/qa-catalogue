package de.gwdg.metadataqa.marc.definition.tags.zbtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;

public class Tag035 extends DataFieldDefinition {

  private static Tag035 uniqueInstance;

  private Tag035() {
    initialize();
    postCreation();
  }

  public static Tag035 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag035();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "035";
    label = "System-Kontrollnummer";
    mqTag = "SystemKontrollnummer";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://access.rdatoolkit.org/Document/Document?documentId=4e62b442-ed67-4236-86f2-186d43ad90d6#035";

    /** 
    * ind1 = new Indicator();
    * ind2 = new Indicator();
     */

    setSubfieldsWithCardinality( 
      "9", "System-Kontrollnummer","NR"
    );

    getSubfield("9").setMqTag("rdf:value");
  }
}
