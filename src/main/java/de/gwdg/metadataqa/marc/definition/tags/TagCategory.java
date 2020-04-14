package de.gwdg.metadataqa.marc.definition.tags;

import java.util.HashMap;
import java.util.Map;

public enum TagCategory {

  tags01x("tags01x", "01X-09X", "Numbers and Code", true),
  tags1xx("tags1xx", "1XX", "Main Entry", true),
  tags20x("tags20x", "20X-24X", "Title", true),
  tags25x("tags25x", "25X-28X", "Edition, Imprint", true),
  tags3xx("tags3xx", "3XX", "Physical Description", true),
  tags4xx("tags4xx", "4XX", "Series Statement", true),
  tags5xx("tags5xx", "5XX", "Note", true),
  tags6xx("tags6xx", "6XX", "Subject Access", true),
  tags70x("tags70x", "70X-75X", "Added Entry", true),
  tags76x("tags76x", "76X-78X", "Linking Entry", true),
  tags80x("tags80x", "80X-83X", "Series Added Entry", true),
  tags84x("tags84x", "841-88X", "Holdings, Location, Alternate Graphics", true),
  dnb("dnbtags", "DNB", "Locally defined tags of DNB", false),
  fennica("fennicatags", "Fennica", "Locally defined tags of Fennica", false),
  gent("genttags", "Gent", "Locally defined tags of Gent", false),
  oclc("oclctags", "OCLC", "OCLCMARC tags", false),
  szte("sztetags", "SZTE", "Locally defined tags of SZTE", false),
  nkcr("nkcrtags", "NKCR", "Locally defined tags of NKCR", false),
  holdings("holdings", "Holdings", "MARC Holdings tags", true)
  ;

  private static Map<String, TagCategory> index = new HashMap<>();

  String packageName;
  String label;
  String range;
  boolean isPartOfMarcCore;

  TagCategory(String packageName, String range, String label, boolean isPartOfMarcCore) {
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
}
