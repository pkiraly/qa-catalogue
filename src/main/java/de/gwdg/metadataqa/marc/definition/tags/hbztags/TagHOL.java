package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Additional Holdings Fields (HOL) from ALMA Publishing
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658
 */
public class TagHOL extends DataFieldDefinition {

  private static TagHOL uniqueInstance;

  private TagHOL() {
    initialize();
    postCreation();
  }

  public static TagHOL getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagHOL();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "HOL";
    label = "Additional Holdings Fields (HOL)";
    mqTag = "AdditionalHoldingsFields";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Created by subfield", "R",
      "b", "Create date subfield", "R",
      "c", "Updated by subfield", "R",
      "d", "Update date subfield", "R",
      "e", "Library name subfield", "R",
      "f", "Location name subfield", "R",
      "g", "Suppress from publishing subfield", "R",
      "M", "Member code subfield", "R",
      "8", "ALMA MMS ID linking HOL to HXX elements"
    );
  }
}
