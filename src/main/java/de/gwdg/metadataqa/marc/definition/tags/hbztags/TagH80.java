package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   	LOCAL 980 (H80) from ALMA Publishing  Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658
 */
public class TagH80 extends DataFieldDefinition {

  private static TagH80 uniqueInstance;

  private TagH80() {
    initialize();
    postCreation();
  }

  public static TagH80 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH80();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H80";
    label = "LOCAL 980 (H80)";
    mqTag = "LOCAL980";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    // TODO: Needs to be adjusted, element is not defined:
    // setSubfieldsWithCardinality(
    //  H80  $8
    //  H80  $a 
    //  H80  $b 
    //  H80  $d 
    //  H80  $k 
    //  H80  $n 
    //  H80  $o 
    //  H80  $p 
    //  H80  $q 
    //  H80  $r 
    //  H80  $t 
    // );
  }
}
