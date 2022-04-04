package de.gwdg.metadataqa.marc;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.regex.Pattern;

public class Range implements Serializable {

  private static final Pattern numericRangePattern = Pattern.compile("^(\\d+)-(\\d+)$");

  private String rangeInput;
  private int min;
  private int max;
  boolean validRange = false;

  public Range(String rangeInput) {
    var matcher = numericRangePattern.matcher(rangeInput);
    if (matcher.find()) {
      min = Integer.parseInt(matcher.group(1));
      max = Integer.parseInt(matcher.group(2));
      validRange = true;
      this.rangeInput = rangeInput;
    } else {
      throw new InvalidParameterException("Invalid range: " + rangeInput);
    }
  }

  public boolean isValid(String value) {
    try {
      var number = Integer.parseInt(value);
      return (min <= number && number <= max);
    } catch(NumberFormatException e) {
      // logger.log(Level.SEVERE, "isValid", e);
    }
    return false;
  }

  public String getRangeInput() {
    return rangeInput;
  }
}
