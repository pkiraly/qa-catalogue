package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.analysis.AuthorityCategory;
import de.gwdg.metadataqa.marc.analysis.ThompsonTraillFields;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Marc21Record extends BibliographicRecord {

  private static List<String> authorityTags;
  private static List<String> claasificationTags;
  private static Map<String, Boolean> authorityTagsIndex;
  private static Map<String, Boolean> claasificationTagsIndex;
  private static Map<String, Map<String, Boolean>> authorityTagsSkippableSubfields;
  private static Map<String, Map<String, Boolean>> claasificationTagsSkippableSubfields;
  private static Map<AuthorityCategory, List<String>> authorityTagsMap;
  private static Map<ThompsonTraillFields, List<String>> ttTagsMap;


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

    if (!authorityTagsSkippableSubfields.containsKey(tag))
      return false;

    return authorityTagsSkippableSubfields.get(tag).getOrDefault(tag, false);
  }

  public boolean isClassificationTag(String tag) {
    if (claasificationTagsIndex == null) {
      initializeAuthorityTags();
    }
    return claasificationTagsIndex.getOrDefault(tag, false);
  }

  public boolean isSkippableClassificationSubfield(String tag, String code) {
    if (claasificationTagsIndex == null)
      initializeAuthorityTags();

    if (!claasificationTagsSkippableSubfields.containsKey(tag))
      return false;

    // System.err.println();
    return claasificationTagsSkippableSubfields.get(tag).getOrDefault(code, false);
  }

  private void initializeAuthorityTags() {
    authorityTags = Arrays.asList(
      "100", "110", "111", "130",
      "700", "710", "711", "730",   "720", "740", "751", "752", "753", "754",
      "800", "810", "811", "830"
    );
    authorityTagsIndex = Utils.listToMap(authorityTags);

    authorityTagsSkippableSubfields = new HashMap<>();

    claasificationTags = Arrays.asList();
    claasificationTagsIndex = Utils.listToMap(claasificationTags);
    // authorityTagsSkippableSubfields.put("028A", Utils.listToMap(Arrays.asList("9", "V", "7", "3")));
    claasificationTagsSkippableSubfields = new HashMap<>();

    authorityTagsMap = new HashMap<>();
    authorityTagsMap.put(AuthorityCategory.Personal, List.of("100", "700", "800"));
    authorityTagsMap.put(AuthorityCategory.Corporate, List.of("110", "710", "810"));
    authorityTagsMap.put(AuthorityCategory.Meeting, List.of("111", "711", "811"));
    authorityTagsMap.put(AuthorityCategory.Geographic, List.of("751", "752"));
    authorityTagsMap.put(AuthorityCategory.Titles, List.of("130", "730", "740", "830"));
    authorityTagsMap.put(AuthorityCategory.Other, List.of("720", "753", "754"));
  }

  private void initializeTTTags() {
    ttTagsMap = new HashMap<>();

    ttTagsMap.put(ThompsonTraillFields.ISBN, Arrays.asList("020"));
    ttTagsMap.put(ThompsonTraillFields.AUTHORS, Arrays.asList("100", "110", "111"));
    ttTagsMap.put(ThompsonTraillFields.ALTERNATIVE_TITLES, Arrays.asList("246"));
    ttTagsMap.put(ThompsonTraillFields.EDITION, Arrays.asList("250"));
    ttTagsMap.put(ThompsonTraillFields.CONTRIBUTORS, Arrays.asList("700", "710", "711", "720"));
    ttTagsMap.put(ThompsonTraillFields.SERIES, Arrays.asList("440", "490", "800", "810", "830"));
    ttTagsMap.put(ThompsonTraillFields.TOC, Arrays.asList("505", "520"));
    ttTagsMap.put(ThompsonTraillFields.DATE_008, Arrays.asList("008/ö7"));
    ttTagsMap.put(ThompsonTraillFields.DATE_26X, Arrays.asList("260$c", "264$c"));
    ttTagsMap.put(ThompsonTraillFields.LC_NLM, Arrays.asList("050", "060", "090"));
    ttTagsMap.put(ThompsonTraillFields.LC_NLM, Arrays.asList("600", "610", "611", "630", "650", "651", "653"));
    ttTagsMap.put(ThompsonTraillFields.MESH, Arrays.asList("600", "610", "611", "630", "650", "651", "653"));
    ttTagsMap.put(ThompsonTraillFields.FAST, Arrays.asList("600", "610", "611", "630", "650", "651", "653"));
    ttTagsMap.put(ThompsonTraillFields.GND, Arrays.asList("600", "610", "611", "630", "650", "651", "653"));
    ttTagsMap.put(ThompsonTraillFields.OTHER, Arrays.asList("600", "610", "611", "630", "650", "651", "653"));
    ttTagsMap.put(ThompsonTraillFields.ONLINE, Arrays.asList("008/23", "300$a")); // 29
    ttTagsMap.put(ThompsonTraillFields.LANGUAGE_OF_RESOURCE, Arrays.asList("008/35"));
    ttTagsMap.put(ThompsonTraillFields.COUNTRY_OF_PUBLICATION, Arrays.asList("008/15"));
  }
}
