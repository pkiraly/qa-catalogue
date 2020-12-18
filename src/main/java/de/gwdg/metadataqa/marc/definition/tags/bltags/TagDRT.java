package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Digital Record Type
 */
public class TagDRT extends DataFieldDefinition {

  private static TagDRT uniqueInstance;

  private TagDRT() {
    initialize();
    postCreation();
  }

  public static TagDRT getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagDRT();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "DRT";
    label = "Digital Record Type";
    mqTag = "digitalRecordType";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Code", "NR"
    );

    getSubfield("a")
      .setCodes(
        "MSD", "Microsoft Digitised Books",
        "VDEP", "Voluntary e-legal deposit records available in Explore the British Library",
        "ETHOS", "Electronic Theses Online Service",
        "LD-ebooks", "Legal deposit e-books"
      )
      .setMqTag("code");
  }
}
