package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.analysis.AuthorityCategory;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Marc21Record extends BibliographicRecord {

  private static List<String> authorityTags;
  private static Map<AuthorityCategory, List<String>> authorityTagsMap;


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

  private void initializeAuthorityTags() {
    authorityTags = Arrays.asList(
      "100", "110", "111", "130",
      "700", "710", "711", "730",   "720", "740", "751", "752", "753", "754",
      "800", "810", "811", "830"
    );

    authorityTagsMap = new HashMap<>();
    authorityTagsMap.put(AuthorityCategory.Personal, List.of("100", "700", "800"));
    authorityTagsMap.put(AuthorityCategory.Corporate, List.of("110", "710", "810"));
    authorityTagsMap.put(AuthorityCategory.Meeting, List.of("111", "711", "811"));
    authorityTagsMap.put(AuthorityCategory.Geographic, List.of("751", "752"));
    authorityTagsMap.put(AuthorityCategory.Titles, List.of("130", "730", "740", "830"));
    authorityTagsMap.put(AuthorityCategory.Other, List.of("720", "753", "754"));
  }
}
