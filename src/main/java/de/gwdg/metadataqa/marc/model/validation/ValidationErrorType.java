package de.gwdg.metadataqa.marc.model.validation;

public enum ValidationErrorType {

  // record
  RECORD_UNDETECTABLE_TYPE(1, "undetectableType", "record: undetectable type", "record"),
  RECORD_INVALID_LINKAGE(2, "invalidLinkage", "record: invalid linkage", "record"),
  RECORD_AMBIGUOUS_LINKAGE(3, "ambiguousLinkage", "record: ambiguous linkage", "record"),
  // control subfield
  CONTROL_SUBFIELD_OBSOLETE_CODE(4, "obsoleteControlSubfield", "control subfield: obsolete code", "control subfield"),
  CONTROL_SUBFIELD_INVALID_CODE(5, "controlValueContainsInvalidCode", "control subfield: invalid code", "control subfield"),
  CONTROL_SUBFIELD_INVALID_VALUE(6, "hasInvalidValue", "control subfield: invalid value", "control subfield"),
  // field
  FIELD_MISSING_REFERENCE_SUBFIELD(7, "missingSubfield", "field: missing reference subfield (880$6)", "field"),
  FIELD_NONREPEATABLE(8, "nonrepeatableField", "field: repetition of non-repeatable field", "field"),
  FIELD_UNDEFINED(9, "undefinedField", "field: undefined field", "field"),
  // indicator
  INDICATOR_OBSOLETE(10, "obsoleteIndicator", "indicator: obsolete value", "indicator"),
  INDICATOR_NON_EMPTY(11, "nonEmptyIndicator", "indicator: non-empty indicator", "indicator"),
  INDICATOR_INVALID_VALUE(12, "hasInvalidValue", "indicator: invalid value", "indicator"),
  // subfield
  SUBFIELD_UNDEFINED(13, "undefinedSubfield", "subfield: undefined subfield", "subfield"),
  SUBFIELD_INVALID_LENGTH(14, "invalidLength", "subfield: invalid length", "subfield"),
  SUBFIELD_INVALID_CLASSIFICATION_REFERENCE(15, "invalidReference", "subfield: invalid classification reference", "subfield"),
  SUBFIELD_PATTERN_MISMATCH(16, "patternMismatch", "subfield: content does not match any patterns", "subfield"),
  SUBFIELD_NONREPEATABLE(17, "nonrepeatableSubfield", "subfield: repetition of non-repeatable subfield", "subfield"),
  SUBFIELD_ISBN(18, "invalidISBN", "subfield: invalid ISBN", "subfield"),
  SUBFIELD_ISSN(19, "invalidISSN", "subfield: invalid ISSN", "subfield"),
  SUBFIELD_UNPARSABLE_CONTENT(20, "unparsableContent", "subfield: content is not well-formatted", "subfield"),
  SUBFIELD_NULL_CODE(21, "nullCode", "subfield: null subfield code", "subfield"),
  SUBFIELD_INVALID_VALUE(22, "hasInvalidValue", "subfield: invalid value", "subfield"),
  ;

  private final int id;
  private final String code;
  private final String message;
  private final String category;

  ValidationErrorType(int id, String code, String message, String category) {
    this.id = id;
    this.code = code;
    this.message = message;
    this.category = category;
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

  public String getCategory() {
    return category;
  }
}
