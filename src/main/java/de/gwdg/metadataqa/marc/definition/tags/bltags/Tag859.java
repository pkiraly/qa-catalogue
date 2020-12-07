package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * National Bibliography Issue Number
 */
public class Tag859 extends DataFieldDefinition {

  private static Tag859 uniqueInstance;

  private Tag859() {
    initialize();
    postCreation();
  }

  public static Tag859 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag859();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "859";
    label = "Digital Resource Flag";
    mqTag = "digitalResourceFlag";
    cardinality = Cardinality.Repeatable;
    obsolete = true;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Digital resource flag", "NR",
      "b", "Category of digital resource", "R"
    );

    getSubfield("a")
      .setCodes(
        "ELD", "Online: Reading Room Only (e-Legal Deposit only)",
        "ORR", "Online: Reading Room Only (subscribed/licensed content specified to be only viewable in the reading rooms)",
        "XLD", "Online (non-Legal Deposit content which is freely available)"
      )
      .setMqTag("flag");

    getSubfield("b")
      .setValidator(new RegexValidator("^.*[^\\.]$"))
      .setMqTag("category");

  }
}
