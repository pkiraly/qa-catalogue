package de.gwdg.metadataqa.marc.definition.tags.b3kattags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Regionale und lokale Kodierungen (MAB 078)
 * https://www.bib-bvb.de/web/b3kat/open-data
 */
public class Tag940 extends DataFieldDefinition {

  private static Tag940 uniqueInstance;

  private Tag940() {
    initialize();
    postCreation();
  }

  public static Tag940 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag940();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "940";
    label = "Regionale und lokale Kodierungen (MAB 078)";
    mqTag = "RegionaleUndLokaleKodierungen";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.bib-bvb.de/web/b3kat/open-data";
    // setCompilanceLevels("O");

    ind1 = new Indicator("")
      .setCodes(
        "1", "",
        "2", ""
      );

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "f", "Selektionskennzeichen Sprachkreis", "NR",
      "n", "Selektionskennzeichen bibliotheksübergreifende Bibliographien und Projekte", "NR",
      "q", "Bibliotheksspezifische Selektionskennzeichen", "NR",
      "r", "ISIL der redigierenden Bibliothek (VD18-Kontext, MAB 088 Unterfeld r)", "NR"
    );

    getSubfield("f").setMqTag("SelektionskennzeichenSprachkreis");
    getSubfield("n").setMqTag("SelektionskennzeichenBibliotheksübergreifendeBibliographienUndProjekte");
    getSubfield("q").setMqTag("BibliotheksspezifischeSelektionskennzeichen");
    getSubfield("r").setMqTag("ISIL");
  }
}
