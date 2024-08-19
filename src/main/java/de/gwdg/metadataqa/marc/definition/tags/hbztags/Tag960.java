package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Selektionskennzeichen NZ
 */
public class Tag960 extends DataFieldDefinition {

  private static Tag960 uniqueInstance;

  private Tag960() {
    initialize();
    postCreation();
  }

  public static Tag960 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag960();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "960";
    label = "Selektionskennzeichen NZ";
    mqTag = "SelektionskennzeichenNZ";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://service-wiki.hbz-nrw.de/nebenbei/cgi/anonymous_display_only.pl?pageId=706314428";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "b",	"bibliographische Sachverhalte", "R",
      "d",	"lizenzfreie Online-Dissertationen", "R",
      "f",	"fremdsprachige Titel", "R",
      "i",	"Internet-Ressourcen", "R",
      "n",	"Nordrhein-Westfälische Bibliographie", "R",
      "o",	"Offline-Datenübernahmen", "R",
      "r",	"frei definierbare Selektionskennzeichen für Bibliotheken", "R",	
      "s",	"Sammlung E-Paper (DNB-Codierung ep)", "R",	
      "u",	"Lizenzhistorie EBooks:Produktsigel", "R",
      "v",	"Lizenzhistorie EBooks:      Lizenzjahr (NW)", "R",
      "w",	"Lizenzhistorie EBooks:  Datum Aufbau des Feldes Lizenzhistorie Books", "R",
      "z",	"SelektionskennzeichenZDB", "R"
    );

    getSubfield("b").setCodes(
        "bestellt", "Bestellaufnahme",
        "AAD", "Alter Druck",
        "Handschrift", "Handschrift",
        "Inkunabel", "Inkunabel",
        "vd18print", "Projekt VD 18",
        "vd18red", "Projekt VD18",
        "ixtheo", "Index Theologie",
        "Mindestkatalogisat", "Katalogisat im Mindeststandard",
        "moj", "Musikalien ohne Jahr",
        "roj", "Retro-Aufnahme ohne Jahr",
        "Schulprogramm", "Schulprogramm",
        "Theaterzettel", "Theaterzettel"
    );

    getSubfield("d").setCodes(
      "lzDiss", "lizenzfreie Online-Dissertation"
    );

    getSubfield("f").setCodes(
    "ara", "arabisch (mit Orig.schr.)",
    "arab", "arabisch",
    "arm", "armenisch (Orig.-schrift)",
    "geo", "georgisch (Orig.-schrift)",
    "griech", "alt- und neugriechisch (mit Orig.schr.)",
    "heb", "hebräisch (mit Orig.schr.)",
    "hebr", "hebräisch (ohne Orig.schr.)",
    "jid", "jiddisch",
    "sla", "kyrillisch (mit Orig.schr.)",
    "kyr", "kyrillisch (ohne Orig.schr.)",
    "oas", "ostasiatisch",
    "chin", "chinesisch (Orig.-schrift)",
    "jap", "japanisch (Orig.-schrift)",
    "korea", "koreanisch (Orig.-schrift)",
    );

    getSubfield("i").setCodes(
      "iall", "Internet-Ressource allg.",
      "OA", "Open Access"
    );

    getSubfield("n").setCodes(
      "NWBib", "Nordrhein-westfälische Bibliographie",
      "NWBib-Zeitschrift", "NWBib-Zeitschrift",
      "prov", "provisor. Aufn. f. NWBib"
    );

    getSubfield("o").setCodes(
      "bdf", "Bibliothek der Frauenfrage in Deutschland"
    );

  }
}
