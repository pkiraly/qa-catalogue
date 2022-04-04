package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;

public interface FieldValidator {
  public static ValidatorResponse isValid(DataField field) {
    return new ValidatorResponse();
  }
}
