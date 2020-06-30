package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag924 extends DataFieldDefinition {

  private static Tag924 uniqueInstance;

  private Tag924() {
    initialize();
    postCreation();
  }

  public static Tag924 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag924();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "924";
    label = "Incipit a nápěv (kramářské tisky)";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Text prvního verše v moderním pravopisu", "NR",
      "b", "První verš incipitu", "NR",
      "c", "Druhý verš incipitu", "NR",
      "d", "Zpívá se jako", "NR"
    );
  }
}
