package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.ISBNValidator;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

public class Tag902 extends DataFieldDefinition {
  private static Tag902 uniqueInstance;

  private Tag902() {
    initialize();
    postCreation();
  }

  public static Tag902 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag902();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "902";
    label = "Nadstandardní ISBN (u seriálů)";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "International Standard Book Number", "NR",
      "c", "Terms of availability", "NR",
      "q", "Qualifying information", "R",
      "z", "Canceled/invalid ISBN", "R"
    );

    getSubfield("a").setValidator(ISBNValidator.getInstance());

    getSubfield("a")
      .setBibframeTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain);

    getSubfield("c")
      .setBibframeTag("acquisitionTerms")
      .setFrbrFunctions(DiscoverySelect, DiscoveryObtain);

    getSubfield("q")
      .setBibframeTag("qualifier");

    getSubfield("z")
      .setMqTag("canceledOrInvalidISBN")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain);
  }
}
