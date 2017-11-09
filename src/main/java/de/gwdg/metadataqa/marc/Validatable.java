package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;

import java.util.List;

public interface Validatable {

	public boolean validate(MarcVersion marcVersion);
	public List<String> getErrors();
	public List<ValidationError> getValidationErrors();
}
