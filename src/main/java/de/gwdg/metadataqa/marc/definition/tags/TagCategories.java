package de.gwdg.metadataqa.marc.definition.tags;

import java.util.Map;
import java.util.TreeMap;

public class TagCategories {

  public final static Map<String, String> categories = new TreeMap<>();
  static {
    categories.put("tags01x", "Numbers and Code");
    categories.put("tags1xx", "Main Entry");
    categories.put("tags20x", "Title");
    categories.put("tags25x", "Edition, Imprint");
    categories.put("tags3xx", "Physical Description");
    categories.put("tags4xx", "Series Statement");
    categories.put("tags5xx", "Note");
    categories.put("tags6xx", "Subject Access");
    categories.put("tags70x", "Added Entry");
    categories.put("tags76x", "Linking Entry");
    categories.put("tags80x", "Series Added Entry");
    categories.put("tags84x", "Holdings, Location, Alternate Graphics");
  }

  public static String getPackage(String packageName) {
    return categories.get(packageName);
  }

}
