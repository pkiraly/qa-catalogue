package de.gwdg.metadataqa.marc.definition;

import java.util.ArrayList;
import java.util.List;

public class ValidatorResponse {
	private boolean isValid = true;
	private List<String> errors = new ArrayList<>();

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean valid) {
		isValid = valid;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void addError(String error) {
		this.errors.add(error);
	}
}
