package de.gwdg.metadataqa.marc.definition;

/**
 * For a compreensive list of MARC serialization formats see
 * https://jorol.github.io/processing-marc/#/serializations
 */
public enum MarcFormat {
  ISO("ISO", "Binary (ISO 2709)"),
  XML("XML", "MARCXML"),
  ALEPHSEQ("ALEPHSEQ", "ALEPHSEQ"),
  LINE_SEPARATED("LINE_SEPARATED", "Line separated binary MARC (each line contains one record)"),
  MARC_LINE("MARC_LINE", "MARC Line")
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
