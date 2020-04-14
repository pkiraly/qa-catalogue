package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag961 extends DataFieldDefinition {

  private static Tag961 uniqueInstance;

  private Tag961() {
    initialize();
    postCreation();
  }

  public static Tag961 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag961();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "961";
    label = "Akce (pouze pro konverzi)";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("O");
    
    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "n", "Název akce", "NR",
      "f", "Formální název akce", "NR",
      "m", "Místo (obec) konání", "NR",
      "z", "Země konání", "NR",
      "p", "Datum zahájení akce", "NR",
      "u", "Datum ukončení akce", "NR",
      "a", "Osoby, role v kódovaném tvaru", "NR",
      "b", "Osoby, role v nekódovaném tvaru", "NR",
      "k", "Osoby, křestní jméno", "NR",
      "j", "Osoby, příjmení", "NR",
      "4", "Osoby, role v kódovaném tvaru (UNIMARC)", "NR",
      "y", "Korporace, role v nekódovaném tvaru", "NR",
      "x", "Název korporace", "NR"
    );
  }
}
