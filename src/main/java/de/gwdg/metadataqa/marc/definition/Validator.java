package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.MarcSubfield;

import java.util.List;

public interface Validator {
	public boolean isValid(String value);
	public boolean isValid(String value, MarcSubfield field);
	public List<String> getErrors();
	public List<ValidationError> getValidationErrors();
}
