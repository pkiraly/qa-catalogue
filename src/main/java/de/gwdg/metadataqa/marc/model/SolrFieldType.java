package de.gwdg.metadataqa.marc.model;

public enum SolrFieldType {

  MARC("marc-tags"),
  HUMAN("human-readable"),
  MIXED("mixed"),
  ;

  private final String type;

  SolrFieldType(String type) {
    this.type = type;
  }

  public static SolrFieldType byCode(String code) {
    for (SolrFieldType type : values()) {
      if (type.type.equalsIgnoreCase(code)) {
        return type;
      }
    }
    return null;
  }

  public String getType() {
    return type;
  }
}
