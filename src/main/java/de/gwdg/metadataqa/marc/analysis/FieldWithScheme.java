package de.gwdg.metadataqa.marc.analysis;

public class FieldWithScheme {
  String tag;
  String schemaName;

  public FieldWithScheme(String tag, String schemaName) {
    this.tag = tag;
    this.schemaName = schemaName;
  }

  public String getTag() {
    return tag;
  }

  public String getSchemaName() {
    return schemaName;
  }

  @Override
  public String toString() {
    return "FieldWithScheme{" +
      "tag='" + tag + '\'' +
      ", schemaName='" + schemaName + '\'' +
      '}';
  }
}
