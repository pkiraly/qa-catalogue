package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import java.util.HashMap;
import java.util.Map;

public class ClassificationSchemes {

  Map<String, String> schemes = new HashMap<>();

  private void initialize() {
    schemes.put("Library of Congress Subject Headings", "lcsh0");
    schemes.put("LC subject headings for children's literature", "lcchild");
    schemes.put("Medical Subject Headings", "mesh");
    schemes.put("National Agricultural Library subject authority file", "nal");
    schemes.put("Source not specified", "unspec");
    schemes.put("Canadian Subject Headings", "cansh");
    schemes.put("Répertoire de vedettes-matière", "rvm");
    schemes.put("NAL subject category code list", "nal");
    schemes.put("Superintendent of Documents Classification System", "sudocs");
    schemes.put("Government of Canada Publications: Outline of Classification", "gcp");
    schemes.put("Library of Congress Classification", "lcc");
    schemes.put("U.S. Dept. of Defense Classification", "usdodc");
    schemes.put("No information provided", "unspec");
    schemes.put("Dewey Decimal classification", "ddc");
    schemes.put("National Library of Medicine classification", "nlm");
    schemes.put("Superintendent of Documents classification", "sudocs");
    schemes.put("Shelving control number", "shelfcn");
    schemes.put("Title", "title");
    schemes.put("Shelved separately", "shelfs");
    schemes.put("Other scheme", "other");

    // name in 080 (udc), 082, 083, 085 (ddc)
    schemes.put("Universal Decimal Classification", "udc");
    schemes.put("Dewey Decimal Classification", "ddc");

    // 055
    schemes.put("LC-based call number assigned by LAC", "lbbcl0");
    schemes.put("Complete LC class number assigned by LAC", "lbbcl1");
    schemes.put("Incomplete LC class number assigned by LAC", "lbbcl2");
    schemes.put("LC-based call number assigned by the contributing library", "lbbcl3");
    schemes.put("Complete LC class number assigned by the contributing library", "lbbcl4");
    schemes.put("Incomplete LC class number assigned by the contributing library", "lbbcl5");
    schemes.put("Other call number assigned by LAC", "lbbcl6");
    schemes.put("Other class number assigned by LAC", "lbbcl7");
    schemes.put("Other call number assigned by the contributing library", "lbbcl8");
    schemes.put("Other class number assigned by the contributing library", "lbbcl9");

    // 653
    // schemes.put("No information provided", "unknown");
    schemes.put("Topical term", "topical");
    schemes.put("Personal name", "personal");
    schemes.put("Corporate name", "corporate");
    schemes.put("Meeting name", "meeting");
    schemes.put("Chronological term", "chronological");
    schemes.put("Geographic name", "geographic");
    schemes.put("Genre/form term", "genre");

    // PICA
    schemes.put("Regensburger Verbundklassifikation", "rvk");
    schemes.put("Regensburger Verbundklassifikation (RVK)", "rvk");
    schemes.put("Sachgruppen der Deutschen Nationalbibliografie bis 2003", "sdnb");
    schemes.put("Sachgruppen der Deutschen Nationalbibliografie ab 2004", "sdnb");
    schemes.put("LCC-Notation", "lcc");
    schemes.put("DDC-Notation", "ddc");
    schemes.put("Notation – Beziehung", "ddc");
    schemes.put("This mixes multiple systems used in DNB before 2004", "dnbsgr");

    schemes.put("LoC Subject Headings", "lcsh0");
    schemes.put("Medical Subject Headings (MeSH)", "mesh");
    schemes.put("Klassifikation der National Library of Medicine (NLM)", "mesh");
    schemes.put("Schlagwortfolgen (DNB und Verbünde)", "dnb");
    schemes.put("FIV-Regionalklassifikation", "fiv");
    schemes.put("FIV-Schlagwörter (Aspekte)", "fiv-asp");
    schemes.put("FIV-Schlagwörter (Themen)", "fiv-them");
    schemes.put("FIV-Sachklassifikation", "fiv-sach");
    schemes.put("Sonstige Notation des FIV", "fiv-sons");
    schemes.put("Erschließung von Musikalien nach Besetzung und Form/Gattung", "mus");
    schemes.put("Schlagwortfolgen (GBV, SWB, K10plus)", "gbv");
    schemes.put("Einzelschlagwörter (Projekte)", "ein");
    schemes.put("Schlagwörter aus einem Thesaurus und freie Schlagwörter", "schl");
    schemes.put("Gattungsbegriffe bei Alten Drucken", "gat");
    schemes.put("Lokale Schlagwörter auf bibliografischer Ebene", "lok-sch");
    schemes.put("Lokale Notationen auf bibliografischer Ebene", "lok-not");
    schemes.put("Allgemeine Systematik für Bibliotheken (ASB)", "asb");
    schemes.put("Systematik der Stadtbibliothek Duisburg (SSD)", "ssd");
    schemes.put("Systematik für Bibliotheken (SfB)", "sfb");
    schemes.put("Klassifikation für Allgemeinbibliotheken (KAB)", "kab");
    schemes.put("Gattungsbegriffe (DNB)", "dnbgat");
    schemes.put("Systematiken der ekz", "ekz");
    schemes.put("STW-Schlagwörter", "stw");
    schemes.put("STW-Schlagwörter - Platzhalter", "stw-platz");
//    schemes.put("STW-Schlagwörter - automatisierte verbale Sacherschließung", "stw-sach"); TODO check if it's stw or stw-sach
    schemes.put("ZBW-Schlagwörter - Veröffentlichungsart", "zbw");
    schemes.put("SSG-Angabe für Fachkataloge", "ssg");
    schemes.put("DDC-Notation: Vollständige Notation", "ddc-not");
    schemes.put("Basisklassifikation", "basis");
    schemes.put("Deutsche Bibliotheksstatistik (DBS)", "dbs");
    schemes.put("SSG-Nummer/FID-Kennzeichen", "ssg-nr");
    schemes.put("SSG-Angabe für thematische OLC-Ausschnitte", "ssg-olc");
    schemes.put("STW-Schlagwörter - automatisierte verbale Sacherschließung", "stw");
    schemes.put("Vorläufige Schlagwörter (STW)", "stw-vor");
    schemes.put("Notation eines Klassifikationssystems", "klas");
    schemes.put("Nicht mehr gültige Notationen der Regensburger Verbundklassifikation (RVK)", "rvk");
  }

  public String resolve(String key) {
    if (schemes.containsKey(key))
      return schemes.get(key);
    throw new IllegalArgumentException(String.format(
      "Key '%s' is not recognized as a classification scheme",
      key));
  }

  private static ClassificationSchemes uniqueInstance;

  private ClassificationSchemes() {
    initialize();
  }

  public static ClassificationSchemes getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new ClassificationSchemes();
    return uniqueInstance;
  }
}
