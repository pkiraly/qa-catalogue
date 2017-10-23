package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.Validatable;

import java.util.ArrayList;
import java.util.List;

public class ControlValue implements Validatable {

	private ControlSubfield definition;
	private String value;
	private List<String> errors;

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

	@Override
	public boolean validate() {
		boolean isValid = true;
		errors = new ArrayList<>();
		if (!definition.getValidCodes().isEmpty()
			&& (!definition.getValidCodes().contains(value) && definition.getCode(value) == null)) {
			if (definition.isRepeatableContent()) {
				int unitLength = definition.getUnitLength();
				for (int i = 0; i < value.length(); i += unitLength) {
					String unit = value.substring(i, i+unitLength);
					if (!definition.getValidCodes().contains(unit)) {
						errors.add(
							String.format(
								"%s/%s (%s) contains an invalid code: '%s' in '%s'",
								definition.getControlField(), definition.formatPositon(), definition.getId(),
								unit, value
							)
						);
						isValid = false;
					}
				}
			} else {
				errors.add(
					String.format(
						"%s/%s (%s) has an invalid value: '%s'",
						definition.getControlField(), definition.formatPositon(), definition.getId(),
						value
					)
				);
				isValid = false;
			}
		}

		return isValid;
	}

	@Override
	public List<String> getErrors() {
		return errors;
	}
}
