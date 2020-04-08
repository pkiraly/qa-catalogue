package de.gwdg.metadataqa.marc.definition.tags.tags84x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

import java.util.Arrays;

/**
 * Matching Information http://www.loc.gov/marc/bibliographic/bd885.html
 */
public class Tag885 extends DataFieldDefinition {

  private static Tag885 uniqueInstance;

  private Tag885() {
    initialize();
    postCreation();
  }

  public static Tag885 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag885();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "885";
    label = "Matching Information";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd885.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality("a", "Matching information", "NR", "b", "Status of matching and its checking", "NR",
        "c", "Confidence value", "NR", "d", "Generation date", "NR", "w", "Record control number", "R", "x",
        "Nonpublic note", "R", "z", "Public note", "R", "0", "Authority record control number or standard number", "R",
        "2", "Source", "NR", "5", "Institution to which field applies", "NR");

    getSubfield("2").setCodeList(OrganizationCodes.getInstance());

    putVersionSpecificSubfields(MarcVersion.NKCR,
        Arrays.asList(new SubfieldDefinition("7", "NKCR Authority ID", "NR")));
  }
}
