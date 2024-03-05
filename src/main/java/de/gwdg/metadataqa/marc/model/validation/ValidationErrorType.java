package de.gwdg.metadataqa.marc.model.validation;

public enum ValidationErrorType {

  // record
  RECORD_UNDETECTABLE_TYPE(1, "undetectableType", "undetectable type", ValidationErrorCategory.RECORD),
  RECORD_INVALID_LINKAGE(2, "invalidLinkage", "invalid linkage", ValidationErrorCategory.RECORD),
  RECORD_AMBIGUOUS_LINKAGE(3, "ambiguousLinkage", "ambiguous linkage", ValidationErrorCategory.RECORD),
  RECORD_PARSING(23, "parsingError", "parsing error", ValidationErrorCategory.RECORD),

  // control subfield
  CONTROL_POSITION_OBSOLETE_CODE(4, "obsoleteControlPosition", "obsolete code", ValidationErrorCategory.CONTROLFIELD),
  CONTROL_POSITION_INVALID_CODE(5, "controlValueContainsInvalidCode", "invalid code", ValidationErrorCategory.CONTROLFIELD),
  CONTROL_POSITION_INVALID_VALUE(6, "invalidValue", "invalid value", ValidationErrorCategory.CONTROLFIELD),

  // field
  FIELD_MISSING_REFERENCE_SUBFIELD(7, "missingSubfield", "missing reference subfield (880$6)", ValidationErrorCategory.DATAFIELD),
  FIELD_NONREPEATABLE(8, "nonrepeatableField", "repetition of non-repeatable field", ValidationErrorCategory.DATAFIELD),
  FIELD_UNDEFINED(9, "undefinedField", "undefined field", ValidationErrorCategory.DATAFIELD),

  // indicator
  INDICATOR_OBSOLETE(10, "obsoleteIndicator", "obsolete value", ValidationErrorCategory.INDICATOR),
  INDICATOR_NON_EMPTY(11, "nonEmptyIndicator", "non-empty indicator", ValidationErrorCategory.INDICATOR),
  INDICATOR_INVALID_VALUE(12, "invalidValue", "invalid value", ValidationErrorCategory.INDICATOR),

  // subfield
  SUBFIELD_UNDEFINED(13, "undefinedSubfield", "undefined subfield", ValidationErrorCategory.SUBFIELD),
  SUBFIELD_INVALID_LENGTH(14, "invalidLength", "invalid length", ValidationErrorCategory.SUBFIELD),
  SUBFIELD_INVALID_CLASSIFICATION_REFERENCE(15, "invalidReference", "invalid classification reference", ValidationErrorCategory.SUBFIELD),
  SUBFIELD_PATTERN_MISMATCH(16, "patternMismatch", "content does not match any patterns", ValidationErrorCategory.SUBFIELD),
  SUBFIELD_NONREPEATABLE(17, "nonrepeatableSubfield", "repetition of non-repeatable subfield", ValidationErrorCategory.SUBFIELD),
  SUBFIELD_ISBN(18, "invalidISBN", "invalid ISBN", ValidationErrorCategory.SUBFIELD),
  SUBFIELD_ISSN(19, "invalidISSN", "invalid ISSN", ValidationErrorCategory.SUBFIELD),
  SUBFIELD_UNPARSABLE_CONTENT(20, "unparsableContent", "content is not well-formatted", ValidationErrorCategory.SUBFIELD),
  SUBFIELD_NULL_CODE(21, "nullCode", "null subfield code", ValidationErrorCategory.SUBFIELD),
  SUBFIELD_INVALID_VALUE(22, "invalidValue", "invalid value", ValidationErrorCategory.SUBFIELD),
  ;

  private final int id;
  private final String code;
  private final String message;
  private final ValidationErrorCategory category;

  ValidationErrorType(int id, String code, String message, ValidationErrorCategory category) {
    this.id = id;
    this.code = code;
    this.message = message;
    this.category = category;
  }

  public static ValidationErrorType byCode(String code) {
    for (ValidationErrorType type : values()) {
      if (code.equals(type.getCode()))
        return type;
    }
    return null;
  }

  public int getId() {
    return id;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public ValidationErrorCategory getCategory() {
    return category;
  }
}
