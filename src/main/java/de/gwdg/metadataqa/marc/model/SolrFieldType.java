package de.gwdg.metadataqa.marc.model;

public enum SolrFieldType {

  MARC("marc-tags"),
  HUMAN("human-readable"),
  MIXED("mixed"),
  ;

  private String type;

  SolrFieldType(String type) {
    this.type = type;
  }

  public static SolrFieldType byCode(String code) {
    for (SolrFieldType type : values())
      if (type.type.toLowerCase().equals(code.toLowerCase()))
        return type;
    return null;
  }

  public String getType() {
    return type;
  }
}
