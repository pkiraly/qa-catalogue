package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import org.apache.spark.sql.catalyst.expressions.Month;
import org.sparkproject.jetty.server.RequestLog.Collection;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Members Information (MBD) from ALMA Publishing
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658
 */
public class TagMBD extends DataFieldDefinition {

  private static TagMBD uniqueInstance;

  private TagMBD() {
    initialize();
    postCreation();
  }

  public static TagMBD getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagMBD();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "MBD";
    label = "Members Information (MBD)";
    mqTag = "MembersInformation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality("M", "Member code subfield", "R",
      "i", "Member BIB MMS ID subfield", "R",
      "n", "Member name subfield", "R"
  );
  }
}
