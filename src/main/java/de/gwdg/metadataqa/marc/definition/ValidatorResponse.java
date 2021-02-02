package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.model.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class ValidatorResponse {
  private boolean isValid = true;
  private List<ValidationError> validationErrors = new ArrayList<>();

  public boolean isValid() {
    return isValid;
  }

  public void setValid(boolean valid) {
    isValid = valid;
  }

  public List<ValidationError> getValidationErrors() {
    return validationErrors;
  }

  public void addValidationError(ValidationError error) {
    this.validationErrors.add(error);
    isValid = false;
  }
}
