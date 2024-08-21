package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Management Information (MNG) from ALMA Publishing
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658
 */
public class TagMNG extends DataFieldDefinition {

  private static TagMNG uniqueInstance;

  private TagMNG() {
    initialize();
    postCreation();
  }

  public static TagMNG getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagMNG();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "MNG";
    label = "Management Information (MNG)";
    mqTag = "ManagementInformation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Created by subfield", "R",
      "b", "Create date subfield", "R",
      "c", "Updated by subfield", "R",
      "d", "Update date subfield", "R",
      "e", "Suppress from publishing subfield", "R",
      "f", "Originating system subfield", "R",
      "g", "Originating system ID subfield", "R",
      "h", "Originating system version subfield", "R",
      "i", "Record format subfield", "R",
      "j", "Cataloging level subfield", "R",
      "k", "Brief level subfield", "R"
    );
  }
}
