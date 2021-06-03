package de.gwdg.metadataqa.marc.definition;

public enum MarcFormat {
  ISO("ISO", "Binary (ISO)"),
  XML("XML", "MARCXML"),
  ALEPHSEQ("ALEPHSEQ", "ALEPHSEQ"),
  LINE_SEPARATED("LINE_SEPARATED", "Line separated"),
  ;

  String code;
  String label;

  MarcFormat(String code, String label) {
    this.code = code;
    this.label = label;
  }

  public static MarcFormat byCode(String code) {
    for (MarcFormat version : values())
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
