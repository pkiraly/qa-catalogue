package de.gwdg.metadataqa.marc.definition.tags;

import java.util.HashMap;
import java.util.Map;

public enum TagCategory {

  tags00x(0, "tags00x", "00X", "Control Fields", true),
  tags01x(1, "tags01x", "01X-09X", "Numbers and Code", true),
  tags1xx(2, "tags1xx", "1XX", "Main Entry", true),
  tags20x(3, "tags20x", "20X-24X", "Title", true),
  tags25x(4, "tags25x", "25X-28X", "Edition, Imprint", true),
  tags3xx(5, "tags3xx", "3XX", "Physical Description", true),
  tags4xx(6, "tags4xx", "4XX", "Series Statement", true),
  tags5xx(7, "tags5xx", "5XX", "Note", true),
  tags6xx(8, "tags6xx", "6XX", "Subject Access", true),
  tags70x(9, "tags70x", "70X-75X", "Added Entry", true),
  tags76x(10, "tags76x", "76X-78X", "Linking Entry", true),
  tags80x(11, "tags80x", "80X-83X", "Series Added Entry", true),
  tags84x(12, "tags84x", "841-88X", "Holdings, Location, Alternate Graphics", true),
  holdings(13, "holdings", "Holdings", "MARC Holdings tags", true),
  oclc(14, "oclctags", "OCLC", "OCLCMARC tags", false),
  dnb(15, "dnbtags", "DNB", "Locally defined tags of DNB", false),
  fennica(16, "fennicatags", "Fennica", "Locally defined tags of Fennica", false),
  gent(17, "genttags", "Gent", "Locally defined tags of Gent", false),
  szte(18, "sztetags", "SZTE", "Locally defined tags of SZTE", false),
  nkcr(19, "nkcrtags", "NKCR", "Locally defined tags of NKCR", false),
  bl(20, "bltags", "BL", "Locally defined tags of the British Library", false),
  uva(21, "uvatags", "UvA", "Locally defined tags of University of Amsterdam", false),
  b3kat(22, "b3kat", "B3Kat", "Locally defined tags of a German union cataogue B3Kat", false),
  other(99, "unknown", "unknown", "unknown origin", false)
  ;

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
