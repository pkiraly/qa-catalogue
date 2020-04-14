package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag978 extends DataFieldDefinition {

  private static Tag978 uniqueInstance;

  private Tag978() {
    initialize();
    postCreation();
  }

  public static Tag978 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag978();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "978";
    label = "Jméno tiskaře ve formalizované podobě";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("O");
    
    ind1 = new Indicator()
      .setCodes(
          "0", "osoba, jméno pod rodným/křestním jménem",
          "1", "osoba, jméno pod příjmením",
          "2", "korporace",
          "3", "rodina",
          "4", "korporace - invertovaná podoba jména"
        );
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Vstupní prvek", "NR",
      "b", "Podřízená složka korporace", "R",
      "c", "Dodatky ke jménu osoby (ne data)", "R",
      "d", "Římská čísla", "NR",
      "f", " Data", "NR",
      "g", "Plná forma iniciál", "R",
      "4", "Role", "R"
    );
  }
}
