package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;

import java.util.List;

public interface Validator {
  public boolean isValid(String value);
  public boolean isValid(String value, MarcSubfield field);
  public List<ValidationError> getValidationErrors();
}
