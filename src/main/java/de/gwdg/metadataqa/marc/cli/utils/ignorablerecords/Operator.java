package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.definition.MarcVersion;

public enum Operator {
  EQUALS("=="),
  DOESNT_EQUAL("!="),
  MATCHES("=~"),
  DOESNT_MATCH("!~"),
  EXISTS("?")
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
