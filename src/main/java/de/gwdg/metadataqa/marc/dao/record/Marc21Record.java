package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.analysis.AuthorityCategory;
import de.gwdg.metadataqa.marc.analysis.ShelfReadyFieldsBooks;
import de.gwdg.metadataqa.marc.analysis.ThompsonTraillFields;
import de.gwdg.metadataqa.marc.dao.DataField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Marc21Record extends BibliographicRecord {

  private static List<String> authorityTags;
  private static List<String> subjectTags;
  private static Map<String, Boolean> authorityTagsIndex;
  private static Map<String, Boolean> subjectTagIndex;
  private static Map<String, Map<String, Boolean>> skippableAuthoritySubfields;
  private static Map<String, Map<String, Boolean>> skippableSubjectSubfields;
  private static Map<AuthorityCategory, List<String>> authorityTagsMap;
  private static Map<ThompsonTraillFields, List<String>> thompsonTraillTagMap;
  private static Map<ShelfReadyFieldsBooks, Map<String, List<String>>> shelfReadyMap;


  public Marc21Record() {
    super();
  }

  public Marc21Record(String id) {
    super(id);
  }

  public List<DataField> getAuthorityFields() {
    if (authorityTags == null) {
      initializeAuthorityTags();
    }
    return getAuthorityFields(authorityTags);
  }

  public Map<DataField, AuthorityCategory> getAuthorityFieldsMap() {
    if (authorityTags == null) {
      initializeAuthorityTags();
    }
    return getAuthorityFields(authorityTagsMap);
  }

  public boolean isAuthorityTag(String tag) {
    if (authorityTagsIndex == null) {
      initializeAuthorityTags();
    }
    return authorityTagsIndex.getOrDefault(tag, false);
  }

  public boolean isSkippableAuthoritySubfield(String tag, String code) {
    if (authorityTagsIndex == null)
      initializeAuthorityTags();

    if (!skippableAuthoritySubfields.containsKey(tag))
      return false;

    return skippableAuthoritySubfields.get(tag).getOrDefault(tag, false);
  }

  public boolean isSubjectTag(String tag) {
    if (subjectTagIndex == null) {
      initializeAuthorityTags();
    }
    return subjectTagIndex.getOrDefault(tag, false);
  }

  public boolean isSkippableSubjectSubfield(String tag, String code) {
    if (subjectTagIndex == null)
      initializeAuthorityTags();

    if (!skippableSubjectSubfields.containsKey(tag))
      return false;

    // System.err.println();
    return skippableSubjectSubfields.get(tag).getOrDefault(code, false);
  }

  private void initializeAuthorityTags() {
    authorityTags = Arrays.asList(
      "100", "110", "111", "130",
      "700", "710", "711", "730",   "720", "740", "751", "752", "753", "754",
      "800", "810", "811", "830"
    );
    authorityTagsIndex = Utils.listToMap(authorityTags);

    skippableAuthoritySubfields = new HashMap<>();

    subjectTags = Arrays.asList(
      "052", "055", "072", "080", "082", "083", "084", "085", "086",
      "600", "610", "611", "630", "647", "648", "650", "651",
      "653", "654", "655", "656", "657", "658", "662"
    );
    subjectTagIndex = Utils.listToMap(subjectTags);
    skippableSubjectSubfields = new HashMap<>();

    authorityTagsMap = new HashMap<>();
    authorityTagsMap.put(AuthorityCategory.Personal, List.of("100", "700", "800"));
    authorityTagsMap.put(AuthorityCategory.Corporate, List.of("110", "710", "810"));
    authorityTagsMap.put(AuthorityCategory.Meeting, List.of("111", "711", "811"));
    authorityTagsMap.put(AuthorityCategory.Geographic, List.of("751", "752"));
    authorityTagsMap.put(AuthorityCategory.Titles, List.of("130", "730", "740", "830"));
    authorityTagsMap.put(AuthorityCategory.Other, List.of("720", "753", "754"));
  }

  public Map<ThompsonTraillFields, List<String>> getThompsonTraillTagsMap() {
    if (thompsonTraillTagMap == null)
      initializeThompsonTraillTags();

    return thompsonTraillTagMap;
  }

  private void initializeThompsonTraillTags() {
    thompsonTraillTagMap = new LinkedHashMap<>();

    thompsonTraillTagMap.put(ThompsonTraillFields.ISBN, Arrays.asList("020"));
    thompsonTraillTagMap.put(ThompsonTraillFields.AUTHORS, Arrays.asList("100", "110", "111"));
    thompsonTraillTagMap.put(ThompsonTraillFields.ALTERNATIVE_TITLES, Arrays.asList("246"));
    thompsonTraillTagMap.put(ThompsonTraillFields.EDITION, Arrays.asList("250"));
    thompsonTraillTagMap.put(ThompsonTraillFields.CONTRIBUTORS, Arrays.asList("700", "710", "711", "720"));
    thompsonTraillTagMap.put(ThompsonTraillFields.SERIES, Arrays.asList("440", "490", "800", "810", "830"));
    thompsonTraillTagMap.put(ThompsonTraillFields.TOC, Arrays.asList("505", "520"));
    thompsonTraillTagMap.put(ThompsonTraillFields.DATE_008, Arrays.asList("008/ö7"));
    thompsonTraillTagMap.put(ThompsonTraillFields.DATE_26X, Arrays.asList("260$c", "264$c"));
    thompsonTraillTagMap.put(ThompsonTraillFields.LC_NLM, Arrays.asList("050", "060", "090"));
    thompsonTraillTagMap.put(ThompsonTraillFields.LC_NLM, Arrays.asList("600", "610", "611", "630", "650", "651", "653"));
    thompsonTraillTagMap.put(ThompsonTraillFields.MESH, Arrays.asList("600", "610", "611", "630", "650", "651", "653"));
    thompsonTraillTagMap.put(ThompsonTraillFields.FAST, Arrays.asList("600", "610", "611", "630", "650", "651", "653"));
    thompsonTraillTagMap.put(ThompsonTraillFields.GND, Arrays.asList("600", "610", "611", "630", "650", "651", "653"));
    thompsonTraillTagMap.put(ThompsonTraillFields.OTHER, Arrays.asList("600", "610", "611", "630", "650", "651", "653"));
    thompsonTraillTagMap.put(ThompsonTraillFields.ONLINE, Arrays.asList("008/23", "300$a")); // 29
    thompsonTraillTagMap.put(ThompsonTraillFields.LANGUAGE_OF_RESOURCE, Arrays.asList("008/35"));
    thompsonTraillTagMap.put(ThompsonTraillFields.COUNTRY_OF_PUBLICATION, Arrays.asList("008/15"));
  }

  public Map<ShelfReadyFieldsBooks, Map<String, List<String>>> getShelfReadyMap() {
    if (shelfReadyMap == null)
      initializeShelfReadyMap();

    return shelfReadyMap;
  }

  private static void initializeShelfReadyMap() {
    shelfReadyMap = new LinkedHashMap<>();

    for (Map.Entry<ShelfReadyFieldsBooks, String> entry : getRawShelfReadyMap().entrySet()) {
      shelfReadyMap.put(entry.getKey(), new TreeMap<>());
      String[] paths = entry.getValue().split(",");
      for (String path : paths) {
        if (path.contains("$")) {
          String[] parts = path.split("\\$");
          if (!shelfReadyMap.get(entry.getKey()).containsKey(parts[0]))
            shelfReadyMap.get(entry.getKey()).put(parts[0], new ArrayList<>());
          shelfReadyMap.get(entry.getKey()).get(parts[0]).add(parts[1]);
        } else {
          shelfReadyMap.get(entry.getKey()).put(path, new ArrayList<>());
        }
      }
    }
  }

  public static Map<ShelfReadyFieldsBooks, String> getRawShelfReadyMap() {
    Map<ShelfReadyFieldsBooks, String> raw = new LinkedHashMap<>();
    raw.put(ShelfReadyFieldsBooks.LDR06, "LDR~06");
    raw.put(ShelfReadyFieldsBooks.LDR07, "LDR~07");
    raw.put(ShelfReadyFieldsBooks.LDR1718, "LDR~17-18");
    raw.put(ShelfReadyFieldsBooks.TAG00600, "006~00");
    raw.put(ShelfReadyFieldsBooks.TAG010, "010$a");
    raw.put(ShelfReadyFieldsBooks.TAG015, "015$a,015$2");
    raw.put(ShelfReadyFieldsBooks.TAG020, "020$a,020$z,020$q");
    raw.put(ShelfReadyFieldsBooks.TAG035, "035$a,035$z");
    raw.put(ShelfReadyFieldsBooks.TAG040, "040$a,040$b,040$c,040$d,040$e");
    raw.put(ShelfReadyFieldsBooks.TAG041, "041$a,041$b,041$h");
    raw.put(ShelfReadyFieldsBooks.TAG050, "050$a,050$b");
    raw.put(ShelfReadyFieldsBooks.TAG082, "082$a,082$2");
    raw.put(ShelfReadyFieldsBooks.TAG1XX, "100$a,110$a,111$a,130$a");
    raw.put(ShelfReadyFieldsBooks.TAG240, "240$a,240$l,240$n,240$p");
    raw.put(ShelfReadyFieldsBooks.TAG245, "245$a,245$b,245$n,245$p,245$c");
    raw.put(ShelfReadyFieldsBooks.TAG246, "246$a,246$b,246$n,246$p");
    raw.put(ShelfReadyFieldsBooks.TAG250, "250$a,250$b");
    raw.put(ShelfReadyFieldsBooks.TAG264, "264$a,264$b,264$c");
    raw.put(ShelfReadyFieldsBooks.TAG300, "300$a,300$b,300$c");
    raw.put(ShelfReadyFieldsBooks.TAG336, "336$a,336$b,336$2");
    raw.put(ShelfReadyFieldsBooks.TAG337, "337$a,337$b,337$2");
    raw.put(ShelfReadyFieldsBooks.TAG338, "338$a,338$b,338$2");
    raw.put(ShelfReadyFieldsBooks.TAG490, "490$a,490$v");
    raw.put(ShelfReadyFieldsBooks.TAG500, "500$a");
    raw.put(ShelfReadyFieldsBooks.TAG504, "504$a");
    raw.put(ShelfReadyFieldsBooks.TAG505, "505$a,505$t,505$r");
    raw.put(ShelfReadyFieldsBooks.TAG520, "520$a");
    raw.put(ShelfReadyFieldsBooks.TAG546, "546$a");
    raw.put(ShelfReadyFieldsBooks.TAG588, "588$a");
    raw.put(ShelfReadyFieldsBooks.TAG6XX, "600$a,610$a,611$a,630$a,647$a,648$a,650$a,651$a,653$a,654$a,655$a,656$a,657$a,658$a,662$a");
    raw.put(ShelfReadyFieldsBooks.TAG7XX, "700$a,710$a,711$a,720$a,730$a,740$a,751$a,752$a,753$a,754$a");
    raw.put(ShelfReadyFieldsBooks.TAG776, "776$a");
    raw.put(ShelfReadyFieldsBooks.TAG856, "856$u");
    raw.put(ShelfReadyFieldsBooks.TAG8XX, "800$a,810$a,811$a,830$a");

    return raw;
  }
}
