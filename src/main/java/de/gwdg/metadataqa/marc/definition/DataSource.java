package de.gwdg.metadataqa.marc.definition;

public enum DataSource {
  FILE("FILE", "from file"),
  STREAM("STREAM", "from stream")
  ;

  String code;
  String label;

  DataSource(String code, String label) {
    this.code = code;
    this.label = label;
  }

  public static DataSource byCode(String code) {
    for (DataSource version : values())
      if (version.code.equals(code))
        return version;
    return null;
  }

  public String getCode() {
    return code;
  }

  public String getLabel() {
    return label;
  }
}
