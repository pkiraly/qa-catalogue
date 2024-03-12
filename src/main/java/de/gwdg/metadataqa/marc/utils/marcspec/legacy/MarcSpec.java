package de.gwdg.metadataqa.marc.utils.marcspec.legacy;

import de.gwdg.metadataqa.marc.utils.SchemaSpec;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Java port of Carsten Klee's PHP MarcSpec class
 * https://github.com/MARCspec/php-marc-spec/blob/26f33207fbe2745c692a70a2832ca48cfc0d68e8/MarcSpec.php
 */
public class MarcSpec implements SchemaSpec, Serializable {

  private static final Pattern fieldTagPattern = Pattern.compile("[X0-9]{3}|LDR");
  private static final Pattern hasSpacePattern = Pattern.compile("\\s");
  private static final Pattern indicatorPattern = Pattern.compile("[a-z0-9_]");
  private static final Pattern subfieldsPattern = Pattern.compile("[a-z0-9!\\\"#$%&'()*+-./:;<=>?]");

  private String fieldTag;
  private Integer charStart;
  private Integer charEnd;
  private Integer charLength;
  private Map<String, String> subfields = new HashMap<>();
  private String indicator1;
  private String indicator2;

  public MarcSpec() {
  }

  public MarcSpec(String spec) {
    if (StringUtils.isNotBlank(spec))
      decode(spec);
  }

  public void decode(String spec) {
    checkIfString(spec);
    validate(spec);
  }

  public boolean validate(String spec) {
    checkIfString(spec);
    clear();
    spec = StringUtils.trim(spec);

    if (3 > spec.length()) {
      throw new IllegalArgumentException("Marc spec must be 3 characters at minimum.");
    }
    if (hasSpacePattern.matcher(spec).matches()) {
      throw new IllegalArgumentException(String.format(
        "For Field Tag of Marc spec no whitespaces are allowed. But \"%s\" given.", spec
      ));
    }
    // check and set field tag
    String fieldTag = spec.substring(0, 3);
    setFieldTag(fieldTag);

    String dataRef = spec.substring(3);
    if (!dataRef.equals("")) {
      if (spec.substring(3, 4).equals("~")) {
        // check character postion or range
        String charPos = spec.substring(4);
        if (!charPos.equals("")) {
          int[] _charPos = validateCharPos(charPos);
          if (_charPos.length > 0) {
            setCharStart(_charPos[0]);
            if (_charPos.length > 1) {
              setCharEnd(_charPos[1]);
            } else {
              setCharLength(1);
              setCharEnd(charStart);
            }
          }
        } else {
          throw new IllegalArgumentException("For character position or range minimum one digit is required. None given.");
        }
      } else {
        String[] _dataRef = validateDataRef(dataRef);
        if (_dataRef != null && _dataRef.length > 0) {
          addSubfields(_dataRef[0]);
          if (_dataRef.length > 1)
            setIndicators(_dataRef[1]);
        }
      }
    }
    return true;
  }

  /**
   * Encode the MarcSpec object as string
   * @return
   */
  public String encode() {
    if (fieldTag == null)
      throw new RuntimeException("No field tag available. Assuming MarcSpec is not initialized.");

    String marcspec = fieldTag;
    if (charStart != null) {
      marcspec += "~" + charStart;

      if (charEnd != null)
        marcspec += "-" + charEnd;

      return marcspec;
    }

    if (!subfields.isEmpty())
      marcspec += StringUtils.join(subfields.keySet(), "");

    if (!(indicator1 == null && indicator2 == null)) {
      if (indicator2 == null) {
        marcspec += "_" + indicator1;
      } else if (indicator1 == null) {
        marcspec += "__" + indicator2;
      } else {
        marcspec += "_" + indicator1 + indicator2;
      }
    }
    return marcspec;
  }

  public Integer getCharStart() {
    return charStart;
  }

  public String getFieldTag() {
    return fieldTag;
  }

  /**
   * Get the character ending position
   * @return
   */
  public Integer getCharEnd() {
    if (charEnd != null) {
      return charEnd;
    } else if (charStart != null && charLength != null) {
      return charStart + charLength - 1;
    } else {
      return null;
    }
  }

  public Integer getCharLength() {
    if (charLength != null) {
      return charLength;
    } else if (charStart != null && charEnd != null) {
      return charEnd - charStart + 1;
    } else {
      return null;
    }
  }

  public Map<String, String> getSubfields() {
    return subfields;
  }

  public List<String> getSubfieldsAsList() {
    return new ArrayList<>(subfields.keySet());
  }

  public String getIndicator1() {
    return indicator1;
  }

  public String getIndicator2() {
    return indicator2;
  }

  public void setIndicators(String arg) {
    checkIfString(arg);
    for (int x = 0; x < arg.length(); x++) {
      String indicator = arg.substring(x, x+1);
      if (0 == x && !"_".equals(indicator))
        setIndicator1(indicator);

      if (1 == x && !"_".equals(indicator))
        setIndicator2(indicator);
    }
  }

  public void setIndicator1(String indicator) {
    if (validateIndicators(indicator))
      indicator1 = indicator;
  }

  public void setIndicator2(String indicator) {
    if (validateIndicators(indicator))
      indicator2 = indicator;
  }

  public boolean addSubfields(String arg) {
    if (charStart != null || "LDR".equals(fieldTag))
      return false;
    checkIfString(arg);
    for (int x = 0; x < arg.length(); x++) {
      String chr = arg.substring(x, x+1);
      subfields.put(chr, chr);
    }
    return true;
  }

  public boolean hasRangeSelector() {
    return charStart != null && charEnd != null;
  }

  public String selectRange(String input) {
    if (input.length() >= charStart) {
      if (input.length() >= charEnd + 1) {
        return input.substring(charStart, charEnd + 1);
      } else {
        return input.substring(charStart);
      }
    }
    return "";
  }

  private String[] validateDataRef(String dataFieldRef) {
    checkIfString(dataFieldRef);

    String[] _ref = dataFieldRef.split("_", 2);

    if (validateSubfields(_ref[0]) && _ref.length > 1)
      validateIndicators(_ref[1]);

    return _ref;
  }

  private boolean validateIndicators(String indicators) {
    checkIfString(indicators);

    if (2 < indicators.length()) {
      throw new IllegalArgumentException(String.format(
        "For indicators only two characters are allowed. \"%d\" characters given.", indicators.length()));
    }
    for (int x = 0; x < indicators.length(); x++) {
      String chr = indicators.substring(x, x+1);
      if (!indicatorPattern.matcher(indicators.substring(x, x+1)).matches()) {
        throw new IllegalArgumentException(String.format(
          "For indicators only digits, lowercase alphabetic characters and \"_\" are allowed. But \"%s\" given. Problematic part: '%s'.",
          indicators, chr
        ));
      }
    }
    return true;
  }

  private boolean validateSubfields(String subfields) {
    for (int x = 0; x < subfields.length(); x++) {
      String chr = subfields.substring(x, x+1);
      if (!subfieldsPattern.matcher(chr).matches()) {
        throw new IllegalArgumentException(String.format(
          "For subfields only digits, lowercase alphabetic characters or one of ... are allowed."
          + " But '%s' given. Problem: '%s'.", subfields, chr));
      }
    }
    return true;
  }

  public void setCharLength(int arg) {
    if (0 < arg) {
      if (charStart == null) {
        throw new RuntimeException(
          "Character start position must be defined first. Use MarcSpec::setCharStart() first to set the character start position.");
      }
      charLength = arg;
      charEnd = charStart + charLength - 1;
      setCharEnd(charEnd);
    } else {
      throw new IllegalArgumentException("Argument must be of type positive int without 0.");
    }
  }

  /**
   * Set the character starting position
   *
   * Length of character range automatically is set if character ending position is set.
   *
   * @param arg
   */
  public void setCharStart(int arg) {
    if (0 <= arg) {
      charStart = arg;
      if (charEnd != null) {
        charLength = charEnd - charStart + 1;
      }
    } else {
      throw new IllegalArgumentException("Argument must be of type int.");
    }
  }

  /**
   * Validate a character position or range
   *
   * @param charPos  The character position or range
   * @return An array of character positions
   */
  private int[] validateCharPos(String charPos) {
    checkIfString(charPos);

    if (charPos.length() < 1) {
      throw new IllegalArgumentException("For character position or range minimum one digit is required. None given.");
    }
    String[] _charPos = charPos.split("-", 3);
    if (2 < _charPos.length) {
      throw new IllegalArgumentException(String.format(
        "For character position or range only digits and one \"-\" is allowed. But \"%s\" given.",
        charPos
      ));
    }
    int[] positions = new int[_charPos.length];
    for (int i = 0; i < _charPos.length; i++) {
      positions[i] = Integer.parseInt(_charPos[i]);
    }

    return positions;
  }

  /**
   * Set the field tag
   *
   * Provided param gets validated
   * @param arg The field tag
   */
  public void setFieldTag(String arg) {
    if (validateFieldTag(arg))
      fieldTag = arg;
  }

  private boolean validateFieldTag(String fieldTag) {
    checkIfString(fieldTag);
    if (!fieldTagPattern.matcher(fieldTag).matches()) {
      throw new IllegalArgumentException(String.format(
        "For Field Tag of Marc spec only digits, \"X\" or \"LDR\" is allowed. But \"%s\"  given.", fieldTag
      ));
    }
    return true;
  }

  private void clear() {
    fieldTag = null;
    charStart = null;
    charEnd = null;
    charLength = null;
    indicator1 = null;
    indicator2 = null;
    subfields = new HashMap<>();
  }

  private void checkIfString(Object arg) {
    if (!(arg instanceof String))
      throw new IllegalArgumentException(String.format(
        "Method decode only accepts string as argument. Given %s.", arg.getClass().getSimpleName()
      ));
  }

  public void setCharEnd(int arg) {
    if (0 <= arg) {
      if (charStart == null) {
        throw new RuntimeException(
          "Character start position must be defined first. Use MarcSpec::setCharStart() first to set the character start position.");
      } else {
        charEnd = arg;
        charLength = charEnd - charStart + 1;
      }
    } else {
      throw new IllegalArgumentException("Argument must be of type positive int or 0.");
    }
  }

  @Override
  public String toString() {
    return "MarcSpec{" +
      "fieldTag='" + fieldTag + '\'' +
      ", charStart=" + charStart +
      ", charEnd=" + charEnd +
      ", charLength=" + charLength +
      ", subfields=" + subfields +
      ", indicator1='" + indicator1 + '\'' +
      ", indicator2='" + indicator2 + '\'' +
      '}';
  }
}
