package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * BGLT (British Grey Literature Team) Report Flag
 */
public class TagBGT extends DataFieldDefinition {

  private static TagBGT uniqueInstance;

  private TagBGT() {
    initialize();
    postCreation();
  }

  public static TagBGT getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagBGT();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "BGT";
    label = "BGLT (British Grey Literature Team) Report Flag";
    mqTag = "BritishGreyLiteratureTeamReportFlag";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");
    obsolete = true;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Report flag", "NR"
    );

    getSubfield("a")
      .setCodes(
        "t", "t"
      )
      .setMqTag("reportFlag");
  }
}
