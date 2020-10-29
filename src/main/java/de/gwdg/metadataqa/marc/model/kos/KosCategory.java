package de.gwdg.metadataqa.marc.model.kos;

public enum KosCategory {
  TERM_LIST("Term List"),
  METADATA_LIKE_MODEL("Metadata-like Model"),
  CLASSIFICATION("Classification and Categorization"),
  RELATIONSHIP_MODEL("Relationship Model");

  private String label;

  KosCategory(String label) {
    this.label = label;
  }
}
