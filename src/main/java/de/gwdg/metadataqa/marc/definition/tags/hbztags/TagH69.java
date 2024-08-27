package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   Lizenzzeiträume in normierter Form	LOCAL 869 (H69) from ALMA Publishing  Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658
 * https://www.alma-dach.org/alma-marc/holdings/869/869.html
 */
public class TagH69 extends DataFieldDefinition {

  private static TagH69 uniqueInstance;

  private TagH69() {
    initialize();
    postCreation();
  }

  public static TagH69 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH69();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H69";
    label = "LOCAL 869 (H69)";
    mqTag = "LOCAL869";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "8", "Sortierhilfe ", "NR",
      "9", "interne Feldnummerierung", "NR",
      "y", "Moving Wall", "NR",
      "a", "Bandzählung", "NR",
      "b", "Heftzählung", "NR",
      "j", "Monatszählung", "NR",
      "k", "Tageszählung", "NR",
      "i", "Jahreszählung", "NR"
    );
  }
}
