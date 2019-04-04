package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;

public interface RecordValidator {

  public static ValidatorResponse isValid(MarcRecord record) {
    return new ValidatorResponse();
  }
}
