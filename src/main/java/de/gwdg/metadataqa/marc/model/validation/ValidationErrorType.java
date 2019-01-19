package de.gwdg.metadataqa.marc.model.validation;

public enum ValidationErrorType {

	// record
	RECORD_UNDETECTABLE_TYPE("undetectableType", "record: undetectable type"),
	RECORD_INVALID_LINKAGE("invalidLinkage", "record: invalid linkage"),
	RECORD_AMBIGUOUS_LINKAGE("ambiguousLinkage", "record: ambiguous linkage"),
	// control subfield
	CONTROL_SUBFIELD_OBSOLETE_CODE("obsoleteControlSubfield", "control subfield: obsolete code"),
	CONTROL_SUBFIELD_INVALID_CODE("controlValueContainsInvalidCode", "control subfield: invalid code"),
	CONTROL_SUBFIELD_INVALID_VALUE("hasInvalidValue", "control subfield: invalid value"),
	// field
	FIELD_MISSING_REFERENCE_SUBFIELD("missingSubfield", "field: missing reference subfield (880$6)"),
	FIELD_NONREPEATABLE("nonrepeatableField", "field: repetition of non-repeatable field"),
	FIELD_UNDEFINED("undefinedField", "field: undefined field"),
	// indicator
	INDICATOR_OBSOLETE("obsoleteIndicator", "indicator: obsolete value"),
	INDICATOR_NON_EMPTY("nonEmptyIndicator", "indicator: non-empty indicator"),
	INDICATOR_INVALID_VALUE("hasInvalidValue", "indicator: invalid value"),
	// subfield
	SUBFIELD_UNDEFINED("undefinedSubfield", "subfield: undefined subfield"),
	SUBFIELD_INVALID_LENGTH("invalidLength", "subfield: invalid length"),
	SUBFIELD_INVALID_CLASSIFICATION_REFERENCE("invalidReference", "subfield: invalid classification reference"),
	SUBFIELD_PATTERN_MISMATCH("patternMismatch", "subfield: content does not match any patterns"),
	SUBFIELD_NONREPEATABLE("nonrepeatableSubfield", "subfield: repetition of non-repeatable subfield"),
	SUBFIELD_ISBN("invalidISBN", "subfield: invalid ISBN"),
	SUBFIELD_ISSN("invalidISSN", "subfield: invalid ISSN"),
	SUBFIELD_UNPARSABLE_CONTENT("unparsableContent", "subfield: content is not well-formatted"),
	SUBFIELD_NULL_CODE("nullCode", "subfield: null subfield code"),
	SUBFIELD_INVALID_VALUE("hasInvalidValue", "subfield: invalid value"),
	;

	private String code;
	private String message;

	ValidationErrorType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
