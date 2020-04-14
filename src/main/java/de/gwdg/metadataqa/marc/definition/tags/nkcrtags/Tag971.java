package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag971 extends DataFieldDefinition {

  private static Tag971 uniqueInstance;

  private Tag971() {
    initialize();
    postCreation();
  }

  public static Tag971 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag971();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "971";
    label = "Signatura UKF (pracovní pole pro retrokonverzi)";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("O");
    
    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Signatura UKF", "NR",
      "b", "Formalizovaná poznámka (Odpis, Vyřazeno, Převedeno do NKCF pod sign. ...)", "NR",
      "c", "Neformalizovaná poznámka", "NR",
      "d", "Služební údaje (přírůstkové číslo)", "R",
      "e", "Označení (pořadí) exempláře (Slovanská knihovna)", "NR",
      "s", "Označení svazku, k němuž se vztahuje přírůstkové číslo", "NR"
    );
  }
}
