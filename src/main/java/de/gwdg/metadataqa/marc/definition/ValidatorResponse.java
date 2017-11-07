package de.gwdg.metadataqa.marc.definition;

import java.util.ArrayList;
import java.util.List;

public class ValidatorResponse {
	private boolean isValid = true;
	private List<String> errors = new ArrayList<>();
	private List<ValidationError> validationErrors = new ArrayList<>();

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean valid) {
		isValid = valid;
	}

	public List<String> getErrors() {
		return errors;
	}

	public List<ValidationError> getValidationErrors() {
		return validationErrors;
	}

	public void addError(String error) {
		this.errors.add(error);
	}

	public void addValidationError(ValidationError error) {
		this.validationErrors.add(error);
	}
}
