package de.gwdg.metadataqa.marc.utils.marcspec;

import org.apache.commons.lang3.StringUtils;

import java.security.InvalidParameterException;

public class Position {

  private Integer positionInt;
  private String positionString;
  private boolean isNumeric = true;

  public Position(Integer positionInt) {
    isNumeric = true;
    this.positionInt = positionInt;
  }

  public Position(String positionString) {
    isNumeric = false;
    if (!positionString.equals("#"))
      throw new InvalidParameterException("Position can be initialized with numbers or '#' only.");
    this.positionString = positionString;
  }

  public Integer getPositionInt() {
    return positionInt;
  }

  public int value() {
    if (positionInt != null)
      return positionInt;
    return -1;
  }

  public String getPositionString() {
    return positionString;
  }

  public String asString() {
    if (StringUtils.isNotBlank(positionString))
      return positionString;
    return "";
  }

  public boolean isNumeric() {
    return isNumeric;
  }

  public boolean isWildcard() {
    return !isNumeric;
  }

  @Override
  public String toString() {
    return "Position{" +
      "int=" + positionInt +
      ", string='" + positionString + '\'' +
      '}';
  }
}
