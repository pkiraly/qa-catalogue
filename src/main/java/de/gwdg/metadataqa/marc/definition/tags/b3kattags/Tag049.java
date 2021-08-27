package de.gwdg.metadataqa.marc.definition.tags.b3kattags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.general.codelist.B3KatIdentifiers;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Besitznachweis (ISIL)
 * https://www.bib-bvb.de/web/b3kat/open-data
 */
public class Tag049 extends DataFieldDefinition {

  private static Tag049 uniqueInstance;

  private Tag049() {
    initialize();
    postCreation();
  }

  public static Tag049 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag049();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "049";
    label = "Besitznachweis (ISIL)";
    mqTag = "BesitznachweisISIL";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.bib-bvb.de/web/b3kat/open-data";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Besitznachweis", "R"
    );

    // TODO: validator: possible ISIL numbers: https://www.bib-bvb.de/BibList/b3kat-biblist.html
    getSubfield("a")
      .setMqTag("Besitznachweis")
      .setCodeList(B3KatIdentifiers.getInstance());
    ;
  }
}
