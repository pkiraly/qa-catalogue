package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;

public interface RecordValidator {

  static ValidatorResponse isValid(BibliographicRecord marcRecord) {
    return new ValidatorResponse();
  }
}
