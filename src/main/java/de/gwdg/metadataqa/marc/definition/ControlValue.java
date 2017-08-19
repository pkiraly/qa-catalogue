package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.ControlSubfield;

public class ControlValue {

	private ControlSubfield definition;
	private String value;

	public ControlValue(ControlSubfield definition, String value) {
		this.definition = definition;
		this.value = value;
	}

	public String getLabel() {
		return definition.getLabel();
	}

	public String getId() {
		return definition.getId();
	}

	public String resolve() {
		return definition.resolve(value);
	}

	public ControlSubfield getDefinition() {
		return definition;
	}

	public String getValue() {
		return value;
	}
}
