package de.gwdg.metadataqa.marc.definition.tags;

import java.util.HashMap;
import java.util.Map;

public enum TagCategory {

  TAGS_00X(0, "tags00x", "00X", "Control Fields", true),
  TAGS_01X(1, "tags01x", "01X-09X", "Numbers and Code", true),
  TAGS_1XX(2, "tags1xx", "1XX", "Main Entry", true),
  TAGS_20X(3, "tags20x", "20X-24X", "Title", true),
  TAGS_25X(4, "tags25x", "25X-28X", "Edition, Imprint", true),
  TAGS_3XX(5, "tags3xx", "3XX", "Physical Description", true),
  TAGS_4XX(6, "tags4xx", "4XX", "Series Statement", true),
  TAGS_5XX(7, "tags5xx", "5XX", "Note", true),
  TAGS_6XX(8, "tags6xx", "6XX", "Subject Access", true),
  TAGS_70X(9, "tags70x", "70X-75X", "Added Entry", true),
  TAGS_76X(10, "tags76x", "76X-78X", "Linking Entry", true),
  TAGS_80X(11, "tags80x", "80X-83X", "Series Added Entry", true),
  TAGS_84X(12, "tags84x", "841-88X", "Holdings, Location, Alternate Graphics", true),
  HOLDINGS(13, "holdings", "Holdings", "MARC Holdings tags", true),
  OCLC(14, "oclctags", "OCLC", "OCLCMARC tags", false),
  DNB(15, "dnbtags", "DNB", "Locally defined tags of DNB", false),
  FENNICA(16, "fennicatags", "Fennica", "Locally defined tags of Fennica", false),
  GENT(17, "genttags", "Gent", "Locally defined tags of Gent", false),
  SZTE(18, "sztetags", "SZTE", "Locally defined tags of SZTE", false),
  NKCR(19, "nkcrtags", "NKCR", "Locally defined tags of NKCR", false),
  BL(20, "bltags", "BL", "Locally defined tags of the British Library", false),
  UVA(21, "uvatags", "UvA", "Locally defined tags of University of Amsterdam", false),
  B3KAT(22, "b3kattags", "B3Kat", "Locally defined tags of a German union cataogue B3Kat", false),
  KBR(23, "kbrtags", "KBR", "Locally defined tags of the Royal Library of Belgium", false),
  ZB(24, "zbtags", "ZB", "Locally defined tags of the Zentralbibliothek Zürich", false),
  PICA_0(50, "pica0", "0...", "PICA+ bibliographic description", false),
  PICA_1(51, "pica1", "1...", "PICA+ holding", false),
  PICA_2(52, "pica2", "2...", "PICA+ item", false),

  // UNIMARC
  UNIMARC_0(60, "tags0--", "0--", "Identification block", false),
  UNIMARC_1(61, "tags1--", "1--", "Coded information block", false),
  UNIMARC_2(62, "tags2--", "2--", "Descriptive information block", false),
  UNIMARC_3(63, "tags3--", "3--", "Notes block", false),
  UNIMARC_4(64, "tags4--", "4--", "Linking entry block", false),
  UNIMARC_5(65, "tags5--", "5--", "Related title block", false),
  UNIMARC_6(66, "tags6--", "6--", "Subject analysis and bibliographic history block", false),
  UNIMARC_7(67, "tags7--", "7--", "Responsibility block", false),
  UNIMARC_8(68, "tags8--", "8--", "International use block", false),
  UNIMARC_9(68, "tags9--", "9--", "National use block", false),
  OTHER(99, "unknown", "unknown", "unknown origin", false);


  private static Map<String, TagCategory> index = new HashMap<>();

  int id;
  String packageName;
  String label;
  String range;
  boolean isPartOfMarcCore;

  TagCategory(int id, String packageName, String range, String label, boolean isPartOfMarcCore) {
    this.id = id;
    this.packageName = packageName;
    this.range = range;
    this.label = label;
    this.isPartOfMarcCore = isPartOfMarcCore;
  }

  public static TagCategory getPackage(String packageName) {
    if (index.size() == 0) {
      for (TagCategory item : values())
        index.put(item.packageName, item);
    }
    return index.getOrDefault(packageName, null);
  }

  public String getPackageName() {
    return packageName;
  }

  public String getLabel() {
    return label;
  }

  public static String getLabel(String packageName) {
    TagCategory cat = getPackage(packageName);
    if (cat != null)
      return cat.getLabel();
    return null;
  }

  public String getRange() {
    return range;
  }

  public boolean isPartOfMarcCore() {
    return isPartOfMarcCore;
  }

  public int getId() {
    return id;
  }
}
