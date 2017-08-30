package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;

public class MarcSubfield {
	private SubfieldDefinition definition;
	private String code;
	private String value;

	public MarcSubfield(SubfieldDefinition definition, String code, String value) {
		this.definition = definition;
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public String getLabel() {
		return definition.getLabel();
	}

	public String resolve() {
		return definition.resolve(value);
	}
}
