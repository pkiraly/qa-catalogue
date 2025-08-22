package de.gwdg.metadataqa.marc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class EncodedValue implements Serializable {
  private static final long serialVersionUID = -8662632239051931116L;
  private String code;
  private String label;
  private boolean isRange = false;
  private boolean isRegex = false;
  private String bibframeTag = null;
  private Range range = null;
  private Map<Integer, String> regexGroups = null;

  public EncodedValue(String code, String label) {
    this.code = code;
    this.label = label;
  }

  public String getCode() {
    return code;
  }

  public String getLabel() {
    return label;
  }

  public boolean isRange() {
    return isRange;
  }

  public void setRange(boolean isRange) {
    if (isRange) {
      this.range = new Range(code);
    }
    this.isRange = isRange;
  }

  public Range getRange() {
    return range;
  }

  public boolean isRegex() {
    return isRegex;
  }

  public void setRegex(boolean regex) {
    isRegex = regex;
    regexGroups = new HashMap<>();
  }

  public Map<Integer, String> getRegexGroups() {
    return regexGroups;
  }

  public void setRegexGroups(Map<Integer, String> regexGroups) {
    this.regexGroups = regexGroups;
  }

  public String getBibframeTag() {
    return bibframeTag;
  }

  public void setBibframeTag(String bibframeTag) {
    this.bibframeTag = bibframeTag;
  }

  @Override
  public String toString() {
    return "Code{" +
        "code='" + code + '\'' +
        ", label='" + label + '\'' +
        '}';
  }
}
