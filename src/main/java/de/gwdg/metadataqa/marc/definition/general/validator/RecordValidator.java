package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;

public interface RecordValidator {

  static ValidatorResponse isValid(MarcRecord marcRecord) {
    return new ValidatorResponse();
  }
}
