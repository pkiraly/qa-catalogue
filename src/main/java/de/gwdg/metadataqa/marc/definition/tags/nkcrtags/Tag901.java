package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag901 extends DataFieldDefinition {

  private static Tag901 uniqueInstance;

  private Tag901() {
    initialize();
    postCreation();
  }

  public static Tag901 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag901();
    return uniqueInstance;
  }
	  
  private void initialize() {
    tag = "901";
    label = "Pomocné údaje agentury ISBN ";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Chybné ISBN (ADNPI a)", "R",
      "b", "ISBN v čárovém kódu (ADCKO a)", "R",
      "c", "Chybné ISBN v čárovém kódu (ADCKO z)", "R",
      "d", "Neoprávněně přidělené ISBN v čárovém kódu (ADCKO y)", "R",
      "e", "ISBN souboru v čárovém kódu (ADCKO s)", "R",
      "f", "Údaje o vydání ISBN (CDVYD a)", "R",
      "g", "Poznámka NA ISBN (DDPOZ a)", "R",
      "h", "Svazek", "R",
      "i", "ISBN svazku", "R",
      "o", "Údaj o ověření", "R"
    );	
  }
}
