package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

public enum Operator {
  EQUAL("=="),
  NOT_EQUAL("!="),
  MATCH("=~"),
  NOT_MATCH("!~"),
  START_WITH("=^"),
  END_WITH("=$"),
  EXIST("?"),
  IN("in"),
  NOT_IN("not in")
  ;

  private String operator;

  Operator(String operator) {
    this.operator = operator;
  }

  public static Operator byCode(String operator) {
    for (Operator item : values())
      if (item.operator.equals(operator))
        return item;
    throw new IllegalArgumentException(String.format("unsupported operator: '%s'", operator));
  }

}
