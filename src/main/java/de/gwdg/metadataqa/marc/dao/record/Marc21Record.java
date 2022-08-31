package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.dao.DataField;

import java.util.Arrays;
import java.util.List;

public class Marc21Record extends BibliographicRecord {
  private static List<String> authorityTags;

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

  private void initializeAuthorityTags() {
    authorityTags = Arrays.asList(
      "100", "110", "111", "130",
      "700", "710", "711", "730",   "720", "740", "751", "752", "753", "754",
      "800", "810", "811", "830"
    );
  }
}
