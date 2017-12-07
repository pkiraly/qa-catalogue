package de.gwdg.metadataqa.marc.model.validation;

public enum ValidationErrorType {

	Obsolete("obsolete", "obsolete value"),
	HasInvalidValue("hasInvalidValue", "invalid value"),
	ContainsInvalidCode("containsInvalidCode", "contains invalid code"),
	UndefinedSubfield("undefinedSubfield", "undefined subfield"),
	InvalidLength("invalidLength", "invalid lengh"),
	MissingSubfield("missingSubfield", "missing subfield"),
	InvalidReference("invalidReference", "invalid reference"),
	NonEmptyIndicator("nonEmptyIndicator", "non-empty indicator"),
	PatternMismatch("patternMismatch", "does not match any patterns"),
	NonrepeatableSubfield("nonrepeatableSubfield", "non-repeatable subfield"),
	NonrepeatableField("nonrepeatableField", "non-repeatable field"),
	UndefinedField("undefinedField", "undefined field"),
	UndetectableType("undetectableType", "undetectable record type"),
	InvalidLinkage("invalidLinkage", "invalid linkage"),
	ISBN("invalidISBN", "invalid ISBN"),
	ISSN("invalidISSN", "invalid ISSN"),
	UnparsableContent("unparsableContent", "invalid format"),
	NullCode("nullCode", "null subfield code"),
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
