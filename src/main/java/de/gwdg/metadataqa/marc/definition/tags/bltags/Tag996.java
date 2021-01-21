package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Z39.50 SFX Enabler
 */
public class Tag996 extends DataFieldDefinition {

  private static Tag996 uniqueInstance;

  private Tag996() {
    initialize();
    postCreation();
  }

  public static Tag996 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag996();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "996";
    label = "Z39.50 SFX Enabler";
    mqTag = "Z3950SfxEnabler";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Code", "NR"
    );

    getSubfield("a")
      .setCodes(
        "KB", "SFX Knowledgebase records",
        "MSD", "Microsoft Digitised Books",
        "VDEP", "Voluntary deposit records available within the OPAC"
      )
      .setMqTag("code");
  }
}
