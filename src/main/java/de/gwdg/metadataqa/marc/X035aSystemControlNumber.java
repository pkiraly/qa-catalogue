package de.gwdg.metadataqa.marc;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 *
 */
public class X035aSystemControlNumber implements Serializable {
  private String code;
  private String number;
  private static final Pattern PATTERN = Pattern.compile("^\\((.{1,10})\\)(.{1,50})$");

  public X035aSystemControlNumber(String code, String number) {
    this.code = code;
    this.number = number;
  }

  public X035aSystemControlNumber(String original) {
    parse035a(original);
  }

  private void parse035a(String original) {
    var matcher = PATTERN.matcher(original);
    if (matcher.find()) {
      this.code = matcher.group(1);
      this.number = matcher.group(2);
    }
  }

  public String getCode() {
    return code;
  }

  public String getNumber() {
    return number;
  }
}
