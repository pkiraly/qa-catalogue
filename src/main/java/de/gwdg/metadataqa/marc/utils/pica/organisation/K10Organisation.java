package de.gwdg.metadataqa.marc.utils.pica.organisation;

public class K10Organisation {

  private String id;
  private String code;
  private String name;

  public K10Organisation(String id, String code, String name) {
    this.id = id;
    this.code = code;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }
}
