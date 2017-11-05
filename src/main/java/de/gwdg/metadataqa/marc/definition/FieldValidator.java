package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.DataField;

public interface FieldValidator {
	public static ValidatorResponse isValid(DataField field) {
		System.err.println("hello interface");
		return new ValidatorResponse();
	}
}
