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

  /**
   * Validate the Marc spec string. If the spec is invalid, then exceptions are thrown. Otherwise, the method gets
   * executed without any exceptions and the specified character positions, indicators and subfields are set.
   * @param spec The Marc spec string to be validated.
   */
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
    String specFieldTag = spec.substring(0, 3);
    setFieldTag(specFieldTag);

    String dataRef = spec.substring(3);
    if (dataRef.isEmpty()) {
      return true;
    }

    // If the spec doesn't contain the positions specifier, then check for data reference
    if (spec.charAt(3) != '~') {
      // The data reference seems to be expected to be in the format of "subfields", "subfields_indicators", or
      // "_indicators".
      String[] splitDataReferences = validateDataRef(dataRef);
      if (splitDataReferences.length == 0) {
        return true;
      }

      addSubfields(splitDataReferences[0]);
      if (splitDataReferences.length > 1) {
        setIndicators(splitDataReferences[1]);
      }
      return true;
    }

    // Check character postion or range
    String charPos = spec.substring(4);
    if (charPos.isEmpty()) {
      throw new IllegalArgumentException("For character position or range minimum one digit is required. None given.");
    }
    int[] positions = validateCharPos(charPos);
    if (positions.length == 0) {
      return true;
    }

    setCharStart(positions[0]);
    if (positions.length > 1) {
      setCharEnd(positions[1]);
    } else {
      setCharLength(1);
      setCharEnd(charStart);
    }
    return true;
  }

  /**
   * Encode the MarcSpec object as string
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
   */
  public Integer getCharEnd() {
    if (charEnd != null) {
      return charEnd;
    }

    if (charStart != null && charLength != null) {
      return charStart + charLength - 1;
    }

    return null;
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
    validateIndicators(indicator);
    indicator1 = indicator;
  }

  public void setIndicator2(String indicator) {
    validateIndicators(indicator);
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

    String[] references = dataFieldRef.split("_", 2);
    validateSubfieldReferences(references[0]);

    if (references.length > 1) {
      validateIndicators(references[1]);
    }

    return references;
  }

  /**
   * Validates that the indicator is a valid character code. If the indicator is invalid, then an exception is thrown.
   * @param indicators Only two characters are allowed. Digits, lowercase alphabetic characters and "_" are allowed.
   */
  private void validateIndicators(String indicators) {
    checkIfString(indicators);

    if (indicators.length() > 2) {
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
  }

  /**
   * Validates that each subfield reference is a valid character code. If the subfield reference is invalid, then an
   * exception is thrown.
   * @param subfieldReferences A string containing the subfield references. It is expected that they are character codes
   *                           for subfields which are merged together into the subfieldReferences string.
   */
  private void validateSubfieldReferences(String subfieldReferences) {
    for (int x = 0; x < subfieldReferences.length(); x++) {
      String chr = subfieldReferences.substring(x, x + 1);
      if (!subfieldsPattern.matcher(chr).matches()) {
        throw new IllegalArgumentException(String.format(
          "For subfields only digits, lowercase alphabetic characters or one of ... are allowed."
            + " But '%s' given. Problem: '%s'.", subfieldReferences, chr));
      }
    }
  }

  public void setCharLength(int charLength) {
    if (charLength <= 0) {
      throw new IllegalArgumentException("Argument must be of type positive int without 0.");
    }

    if (charStart == null) {
      throw new RuntimeException(
        "Character start position must be defined first. Use MarcSpec::setCharStart() first to set the character start position.");
    }
    this.charLength = charLength;
    charEnd = charStart + this.charLength - 1;
    setCharEnd(charEnd);
  }

  /**
   * Set the character starting position.
   * Length of character range automatically is set if character ending position is set.
   *
   * @param charStart The character starting position.
   */
  public void setCharStart(int charStart) {
    if (0 > charStart) {
      throw new IllegalArgumentException("Argument must be of type int.");
    }

    this.charStart = charStart;
    if (charEnd != null) {
      charLength = charEnd - this.charStart + 1;
    }
  }

  /**
   * Validate a character position or range. The charPos parameter must be in format of "position" or "startPos-endPos",
   * where positions are integers.
   * @param charPos  The character position or range in a string format.
   * @return An array of character positions. It's expected that the array always has at most two elements: the start
   * and end position of the range.
   */
  private int[] validateCharPos(String charPos) {
    checkIfString(charPos);

    if (charPos.isEmpty()) {
      throw new IllegalArgumentException("For character position or range minimum one digit is required. None given.");
    }

    String[] splitCharPos = charPos.split("-", 3);
    if (splitCharPos.length > 2) {
      throw new IllegalArgumentException(String.format(
        "For character position or range only digits and one \"-\" is allowed. But \"%s\" given.",
        charPos
      ));
    }

    int[] positions = new int[splitCharPos.length];
    for (int i = 0; i < splitCharPos.length; i++) {
      positions[i] = Integer.parseInt(splitCharPos[i]);
    }

    return positions;
  }

  /**
   * Sets the field tag. The provided tag first gets validated and an InvalidArgumentException
   * is thrown if the tag is invalid.
   * @param specFieldTag The field tag to be validated and set.
   */
  public void setFieldTag(String specFieldTag) {
    validateFieldTag(specFieldTag);
    fieldTag = specFieldTag;
  }

  private void validateFieldTag(String fieldTag) {
    checkIfString(fieldTag);
    if (!fieldTagPattern.matcher(fieldTag).matches()) {
      throw new IllegalArgumentException(String.format(
        "For Field Tag of Marc spec only digits, \"X\" or \"LDR\" is allowed. But \"%s\"  given.", fieldTag
      ));
    }
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
    if (!(arg instanceof String)) {
      throw new IllegalArgumentException(String.format(
        "Method decode only accepts string as argument. Given %s.", arg.getClass().getSimpleName()
      ));
    }
  }

  public void setCharEnd(int charEnd) {
    if (charEnd < 0) {
      throw new IllegalArgumentException("Argument must be of type positive int or 0.");
    }

    if (charStart == null) {
      throw new RuntimeException(
        "Character start position must be defined first. Use MarcSpec::setCharStart() first to set the character start position.");
    }
    this.charEnd = charEnd;
    charLength = this.charEnd - charStart + 1;
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
