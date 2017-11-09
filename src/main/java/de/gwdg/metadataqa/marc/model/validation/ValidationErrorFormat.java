package de.gwdg.metadataqa.marc.model.validation;

public enum ValidationErrorFormat {

	TAB_SEPARATED("tab-separated", "tab-separated"),
	COMMA_SEPARATED("comma-separated", "comma-separated"),
	TEXT("text", "simple text"),
	JSON("json", "JSON")
	;

	private String name;
	private String description;

	ValidationErrorFormat(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
