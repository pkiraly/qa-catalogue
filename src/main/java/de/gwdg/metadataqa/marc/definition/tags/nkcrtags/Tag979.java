package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

public class Tag979 extends DataFieldDefinition {

  private static Tag979 uniqueInstance;

  private Tag979() {
    initialize();
    postCreation();
  }

  public static Tag979 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag979();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "979";
    label = "Sídlo redakce";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("O");
    
    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "n", "Název", "NR",
      "k", "Křestní jméno osoby", "NR",
      "p", "Příjmení osoby", "NR",
      "n", "Doplňky ke jménu osoby", "NR",
      "i", "Příjmení šéfredaktora", "NR",
      "j", "Křestní jméno šéfredaktora", "NR",
      "a", "Adresa", "NR",
      "b", "Adresa pro písemný styk", "NR",
      "e", "Telefon", "NR",
      "f", "Fax", "NR",
      "x", "E-mail", "NR",
      "y", "Datum aktualizace", "NR"
    );
  }
}
