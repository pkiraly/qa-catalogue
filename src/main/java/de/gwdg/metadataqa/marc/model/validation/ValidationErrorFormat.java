package de.gwdg.metadataqa.marc.model.validation;

import java.util.Arrays;
import java.util.List;

public enum ValidationErrorFormat {

	TAB_SEPARATED("tab separated", "tsv", "tab-separated"),
	COMMA_SEPARATED("comma separated", "csv", "comma-separated"),
	TEXT("simple text", "text", "txt"),
	JSON("JSON", "json")
	;

	private List<String> names;
	private String description;
	private String abbreviation = null;

	ValidationErrorFormat(String description, String... names) {
		this.description = description;
		this.names = Arrays.asList(names);
	}

	public String getName() {
		return names.get(0);
	}

	public List<String> getNames() {
		return names;
	}

	public String getDescription() {
		return description;
	}
}
