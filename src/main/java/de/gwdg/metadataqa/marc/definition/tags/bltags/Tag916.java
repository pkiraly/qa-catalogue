package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Authority Control Information
 */
public class Tag916 extends DataFieldDefinition {

  private static Tag916 uniqueInstance;

  private Tag916() {
    initialize();
    postCreation();
  }

  public static Tag916 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag916();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "916";
    label = "Authority Control Information";
    mqTag = "authorityControl";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Authority control information", "R"
    );

    getSubfield("a")
      .setValidator(new RegexValidator("^.*[^\\.]$"))
      .setMqTag("authorityControl");
  }
}
