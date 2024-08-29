package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   Schlagwörter und Schlagwortketten (nicht RSWK) - LOCAL 695 (H95) from ALMA Publishing  Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658
 * https://www.alma-dach.org/alma-marc/holdings/695/695.html
 */
public class TagH95 extends DataFieldDefinition {

  private static TagH95 uniqueInstance;

  private TagH95() {
    initialize();
    postCreation();
  }

  public static TagH95 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH95();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H95";
    label = "LOCAL 695 (H95)";
    mqTag = "LOCAL695";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "...", "R",
      "b", "...", "R",
      "9", "...", "R",
      "x", "...", "R",
      "v", "...", "R",
      "8", "ALMA MMS ID linking HOL to HXX elements","R"
    );
  }
}
