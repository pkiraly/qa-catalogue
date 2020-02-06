package de.gwdg.metadataqa.marc.utils.pica;

public class PicaSubfield {
  private String code;
  private String value;

  public PicaSubfield(String code, String value) {
    this.code = code;
    this.value = value;
  }

  public String getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "PicaSubfield{" +
      "code='" + code + '\'' +
      ", value='" + value + '\'' +
      '}';
  }

  public String format() {
    return String.format("%s) %s", code, value);
  }
}
