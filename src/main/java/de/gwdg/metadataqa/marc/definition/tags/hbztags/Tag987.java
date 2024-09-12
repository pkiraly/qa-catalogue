package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Selektionskennzeichen IZ
 */
public class Tag987 extends DataFieldDefinition {

  private static Tag987 uniqueInstance;

  private Tag987() {
    initialize();
    postCreation();
  }

  public static Tag987 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag987();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "987";
    label = "Selektionskennzeichen IZ";
    mqTag = "SelektionskennzeichenIZ";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";

    ind1 = new Indicator("undefined")
    .setCodes(  
      "1", "..."
    )
    .setMqTag("undefined");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Mikrocodierungen", "R"
    );


  }
}
