package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.analysis.AuthorityCategory;
import de.gwdg.metadataqa.marc.analysis.ThompsonTraillFields;
import de.gwdg.metadataqa.marc.dao.DataField;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Marc21Record extends BibliographicRecord {

  private static List<String> authorityTags;
  private static List<String> subjectTags;
  private static Map<String, Boolean> authorityTagsIndex;
  private static Map<String, Boolean> subjectTagIndex;
  private static Map<String, Map<String, Boolean>> skippableAuthoritySubfields;
  private static Map<String, Map<String, Boolean>> skippableSubjectSubfields;
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
