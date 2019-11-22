package de.gwdg.metadataqa.marc.model.validation;

public enum ValidationErrorType {

  // record
  RECORD_UNDETECTABLE_TYPE("undetectableType", "record: undetectable type", "record"),
  RECORD_INVALID_LINKAGE("invalidLinkage", "record: invalid linkage", "record"),
  RECORD_AMBIGUOUS_LINKAGE("ambiguousLinkage", "record: ambiguous linkage", "record"),
  // control subfield
  CONTROL_SUBFIELD_OBSOLETE_CODE("obsoleteControlSubfield", "control subfield: obsolete code", "control subfield"),
  CONTROL_SUBFIELD_INVALID_CODE("controlValueContainsInvalidCode", "control subfield: invalid code", "control subfield"),
  CONTROL_SUBFIELD_INVALID_VALUE("hasInvalidValue", "control subfield: invalid value", "control subfield"),
  // field
  FIELD_MISSING_REFERENCE_SUBFIELD("missingSubfield", "field: missing reference subfield (880$6)", "field"),
  FIELD_NONREPEATABLE("nonrepeatableField", "field: repetition of non-repeatable field", "field"),
  FIELD_UNDEFINED("undefinedField", "field: undefined field", "field"),
  // indicator
  INDICATOR_OBSOLETE("obsoleteIndicator", "indicator: obsolete value", "indicator"),
  INDICATOR_NON_EMPTY("nonEmptyIndicator", "indicator: non-empty indicator", "indicator"),
  INDICATOR_INVALID_VALUE("hasInvalidValue", "indicator: invalid value", "indicator"),
  // subfield
  SUBFIELD_UNDEFINED("undefinedSubfield", "subfield: undefined subfield", "subfield"),
  SUBFIELD_INVALID_LENGTH("invalidLength", "subfield: invalid length", "subfield"),
  SUBFIELD_INVALID_CLASSIFICATION_REFERENCE("invalidReference", "subfield: invalid classification reference", "subfield"),
  SUBFIELD_PATTERN_MISMATCH("patternMismatch", "subfield: content does not match any patterns", "subfield"),
  SUBFIELD_NONREPEATABLE("nonrepeatableSubfield", "subfield: repetition of non-repeatable subfield", "subfield"),
  SUBFIELD_ISBN("invalidISBN", "subfield: invalid ISBN", "subfield"),
  SUBFIELD_ISSN("invalidISSN", "subfield: invalid ISSN", "subfield"),
  SUBFIELD_UNPARSABLE_CONTENT("unparsableContent", "subfield: content is not well-formatted", "subfield"),
  SUBFIELD_NULL_CODE("nullCode", "subfield: null subfield code", "subfield"),
  SUBFIELD_INVALID_VALUE("hasInvalidValue", "subfield: invalid value", "subfield"),
  ;

  private String code;
  private String message;
  private String category;

  ValidationErrorType(String code, String message, String category) {
    this.code = code;
    this.message = message;
    this.category = category;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String getCategory() {
    return category;
  }
}
