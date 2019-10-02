package de.gwdg.metadataqa.marc.definition.general.indexer;

import de.gwdg.metadataqa.marc.definition.general.codelist.GenreFormCodeAndTermSourceCodes;

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
  }

  public String resolve(String key) {
    if (schemes.containsKey(key))
      return schemes.get(key);
    throw new IllegalArgumentException(String.format("Key '%s' is not recognized as a classification scheme", key));
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
