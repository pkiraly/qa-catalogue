package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import java.util.regex.Pattern;

public class Condition {
  String tag;
  String subfield;
  Operator operator;
  String value;
  Pattern pattern;
  boolean useEqual = false;
  boolean usePattern = false;
  boolean negate = false;

  public Condition(String tag, String subfield, Operator operator, String value) {
    this.tag = tag;
    this.subfield = subfield;
    this.operator = operator;
    this.value = value;
    if (operator == Operator.MATCH || operator == Operator.NOT_MATCH) {
      pattern = Pattern.compile(value);
      usePattern = true;
      if (operator == Operator.NOT_MATCH)
        negate = true;
    } else if (operator == Operator.START_WITH) {
      pattern = Pattern.compile("^" + value);
      usePattern = true;
    } else if (operator == Operator.END_WITH) {
      pattern = Pattern.compile(value + "$");
      usePattern = true;
    } else if (operator == Operator.EQUAL1 || operator == Operator.EQUAL) {
      useEqual = true;
    } else if (operator == Operator.NOT_EQUAL) {
      useEqual = true;
      negate = true;
    }
  }

  public String getTag() {
    return tag;
  }

  public String getSubfield() {
    return subfield;
  }

  public Operator getOperator() {
    return operator;
  }

  public String getValue() {
    return value;
  }

  public Pattern getPattern() {
    return pattern;
  }

  public boolean isUseEqual() {
    return useEqual;
  }

  public boolean isUsePattern() {
    return usePattern;
  }

  public boolean isNegate() {
    return negate;
  }

  @Override
  public String toString() {
    return "Condition{" +
      "tag='" + tag + '\'' +
      ", subfield='" + subfield + '\'' +
      ", operator=" + operator +
      ", value='" + value + '\'' +
      ", pattern=" + pattern +
      ", useEqual=" + useEqual +
      ", usePattern=" + usePattern +
      ", negate=" + negate +
      '}';
  }
}
