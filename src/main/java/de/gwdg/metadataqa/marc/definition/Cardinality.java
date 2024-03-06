package de.gwdg.metadataqa.marc.definition;

public enum Cardinality {
  Repeatable("R"),
  Nonrepeatable("NR");

  String code;

  Cardinality(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public static Cardinality byCode(String code) {
    for (Cardinality cardinality : values())
      if (cardinality.code.equals(code))
        return cardinality;
    return null;
  }

  public static Cardinality get(boolean repeatable) {
    return repeatable ? Repeatable : Nonrepeatable;
  }
}
