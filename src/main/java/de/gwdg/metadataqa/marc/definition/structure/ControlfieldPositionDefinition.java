package de.gwdg.metadataqa.marc.definition.structure;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.general.codelist.CodeList;
import de.gwdg.metadataqa.marc.definition.general.parser.SubfieldContentParser;
import de.gwdg.metadataqa.marc.definition.general.validator.SubfieldValidator;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class ControlfieldPositionDefinition implements Serializable {

  protected static final Pattern TRIMMABLE = Pattern.compile("^[^ ]+ +$");
  private static final long serialVersionUID = 1094865179514850215L;

  protected String id;
  protected String label;
  protected String bibframeTag;
  protected String mqTag;
  protected int positionStart;
  protected int positionEnd;
  protected boolean hasCodelist = true;
  protected List<EncodedValue> codes = new ArrayList<>();
  protected List<EncodedValue> historicalCodes;
  /**
   * Represents a list of valid codes extracted from the codes list. It serves as a cache for the list of valid codes
   * which aren't regex patterns, but simple string codes.
   */
  protected List<String> validCodes = new ArrayList<>();
  /**
   * Used in case the codes are separately defined in some code list. Used mostly if the list of codes would otherwise
   * be frequently repeated in the definition.
   */
  protected CodeList codeList;
  protected ControlfieldPositionDefinition codeListReference;

  protected int unitLength = -1;
  protected boolean repeatableContent = false;
  protected String defaultCode;
  protected String descriptionUrl;
  protected SubfieldContentParser parser;
  protected List<FRBRFunction> functions;
  private SubfieldValidator validator;

  public ControlfieldPositionDefinition() {}

  public ControlfieldPositionDefinition(String label, int positionStart, int positionEnd) {
    this.label = label;
    this.positionStart = positionStart;
    this.positionEnd = positionEnd;
  }

  public ControlfieldPositionDefinition(String label, int positionStart, int positionEnd,
                                        List<EncodedValue> codes) {
    this(label, positionStart, positionEnd);
    this.codes = codes;
    extractValidCodes();
  }

  public ControlfieldPositionDefinition setCodes(List<EncodedValue> codes) {
    this.codes = codes;
    extractValidCodes();
    return this;
  }

  public String getLabel() {
    return label;
  }

  public String getBibframeTag() {
    return bibframeTag;
  }

  public ControlfieldPositionDefinition setBibframeTag(String bibframeTag) {
    this.bibframeTag = bibframeTag;
    return this;
  }

  public String getMqTag() {
    return mqTag;
  }

  public ControlfieldPositionDefinition setMqTag(String mqTag) {
    this.mqTag = mqTag;
    return this;
  }

  public int getPositionStart() {
    return positionStart;
  }

  public int getPositionEnd() {
    return positionEnd;
  }

  public List<EncodedValue> getCodes() {
    return codes;
  }

  public int getUnitLength() {
    return unitLength;
  }

  public String getId() {
    return id;
  }

  public ControlfieldPositionDefinition setId(String id) {
    this.id = id;
    return this;
  }

  public ControlfieldPositionDefinition setUnitLength(int unitLength) {
    this.unitLength = unitLength;
    return this;
  }

  public boolean isRepeatableContent() {
    return repeatableContent;
  }

  public ControlfieldPositionDefinition setRepeatableContent(boolean repeatableContent) {
    this.repeatableContent = repeatableContent;
    return this;
  }

  public String getDefaultCode() {
    return defaultCode;
  }

  public ControlfieldPositionDefinition setCodeList(CodeList codeList) {
    this.codeList = codeList;
    return this;
  }

  public CodeList getCodeList() {
    return codeList;
  }


  public ControlfieldPositionDefinition setDefaultCode(String defaultCode) {
    this.defaultCode = defaultCode;
    return this;
  }

  public String getDescriptionUrl() {
    return descriptionUrl;
  }

  public boolean validate(String code) {
    // Blanks should probably be identified somehow differently
    // In the avram-unimarc schema, they are represented as a ' ' character, but in the records they can also be '#' or
    // '-', or even '^' OR EVEN '|' for some reason

    // The codes could also be validated case insensitively
    // Get the regex pattern if it exists. The current implementation assumes at most one regex pattern per code,
    // and it also assumes that the regex pattern is a valid regex pattern

    boolean regexValid = validateRegexPattern(code);

    if (!regexValid) {
      return false;
    }

    // Now check if the code is valid
    if (codes == null || codes.isEmpty()) {
      return true;
    }

    if (repeatableContent) {
      return validateRepeatableCode(code);
    }

    return validateNonRepeatableCode(code);
  }

  private boolean validateRegexPattern(String code) {
    EncodedValue regexPattern = codes.stream().filter(EncodedValue::isRegex).findFirst().orElse(null);

    if (regexPattern == null) {
      return true;
    }

    // Create a pattern from the regex pattern
    // The pattern should be case-insensitive

    Pattern pattern = Pattern.compile(regexPattern.getCode(), Pattern.CASE_INSENSITIVE);

    // Check if the code matches the pattern
    Matcher patternMatcher = pattern.matcher(code);

    // There is no checking for groups, so that should be implemented as well
    // TODO implement group checking

    return patternMatcher.matches();
  }

  /**
   * Validates repeatable codes. The method assumes that the code is repeatable and that all codes occur at equal
   * distances from each other.
   * In other words, if the unitLength is 3, then only the substring at positions divisible by 3 are checked.
   * @param code The code to validate
   * @return True if the code is valid, false otherwise
   */
  private boolean validateRepeatableCode(String code) {
    for (int i = 0; i < code.length(); i += unitLength) {
      String unit = code.substring(i, i + unitLength);
      if (!validCodes.contains(unit)) {
        return false;
      }
    }
    return true;
  }

  private boolean validateNonRepeatableCode(String code) {
    return validCodes.stream()
      .anyMatch(e -> e.equals(code));
  }

  public String resolve(String inputCode) {
    if (codes == null && codeList == null) {
      return inputCode;
    }

    if (repeatableContent) {
      inputCode = resolveRepeatable(inputCode);
    } else {
      inputCode = resolveSingleCode(inputCode);
    }

    return inputCode;
  }

  private String resolveRepeatable(String inputCode) {
    List<String> units = new ArrayList<>();
    for (int i=0; i < inputCode.length(); i += unitLength) {
      String unit = inputCode.substring(i, i+unitLength);
      if (!units.contains(unit))
        units.add(unit);
    }
    List<String> resolved = new ArrayList<>();
    for (String unit : units)
      resolved.add(resolveSingleCode(unit));

    inputCode = StringUtils.join(resolved, ", ");
    return inputCode;
  }

  private String resolveSingleCode(String inputCode) {
    if (codeList != null) {
      if (inputCode.length() > 1 && TRIMMABLE.matcher(inputCode).matches()) {
        String trimmed = inputCode.trim();
        if (codeList.isValid(trimmed))
          return codeList.getCode(trimmed).getLabel();
      }
      if (codeList.isValid(inputCode.trim()))
        return codeList.getCode(inputCode.trim()).getLabel();
    }

    if (codes != null) {
      for (EncodedValue code : codes)
        if (code.getCode().equals(inputCode))
          return code.getLabel();
    }

    return inputCode;
  }

  protected void extractValidCodes() {
    if (codes == null) {
      return;
    }
    for (EncodedValue code : codes) {
      if (!code.isRegex()) {
        validCodes.add(code.getCode());
      }
    }
  }

  public List<String> getValidCodes() {
    return validCodes;
  }

  public String formatPositon() {
    return (positionStart == positionEnd - 1)
      ? String.format("%02d", positionStart)
      : String.format("%02d-%02d", positionStart, positionEnd-1);
  }

  public String getControlField() {
    // The original implementation used to be quite closely tied to the original MARC21-in-code representation.
    // For now, I'm going to resort to a hacky approach where we use the ID of the control field
    // TODO rethink and reimplement this

    if (id.toLowerCase().startsWith("leader"))
      return "Leader";

    return id.substring(0, 3);
  }

  public EncodedValue getCode(String otherCode) {
    for (EncodedValue code : codes)
      if (code.getCode().equals(otherCode))
        return code;
      else if (code.isRange() && code.getRange().isValid(otherCode))
        return code;

    return null;
  }

  public boolean isHistoricalCode(String inputCode) {
    if (historicalCodes != null && !historicalCodes.isEmpty())
      for (EncodedValue historicalCode : historicalCodes)
        if (historicalCode.getCode().equals(inputCode))
          return true;
    return false;
  }

  public String getPath() {
    return getPath(true);
  }

  public String getPath(boolean showId) {
    if (showId)
      return String.format("%s/%s (%s)", getControlField(), formatPositon(), getId());
    else
      return String.format("%s/%s", getControlField(), formatPositon());
  }

  public boolean hasParser() {
    return parser != null;
  }

  public SubfieldContentParser getParser() {
    return parser;
  }

  public List<EncodedValue> getHistoricalCodes() {
    return historicalCodes;
  }

  public List<FRBRFunction> getFrbrFunctions() {
    return functions;
  }

  public SubfieldValidator getValidator() {
    return validator;
  }

  public ControlfieldPositionDefinition setValidator(SubfieldValidator validator) {
    this.validator = validator;
    return this;
  }

  public boolean hasCodelist() {
    return hasCodelist;
  }

  public ControlfieldPositionDefinition hasCodelist(boolean hasCodelist) {
    this.hasCodelist = hasCodelist;
    return this;
  }

  public ControlfieldPositionDefinition getCodeListReference() {
    return codeListReference;
  }

  public ControlfieldPositionDefinition setCodeListReference(ControlfieldPositionDefinition codeListReference) {
    this.codeListReference = codeListReference;
    return this;
  }

  @Override
  public String toString() {
    return "ControlfieldPositionDefinition{" +
        "label='" + label + '\'' +
        ", positionStart=" + positionStart +
        ", positionEnd=" + positionEnd +
        ", codes=" + codes +
        ", unitLength=" + unitLength +
        ", repeatableContent=" + repeatableContent +
        ", mqTag=" + mqTag +
        ", id=" + id +
        '}';
  }
}
