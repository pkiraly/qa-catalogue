package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.MarcRecord;

public interface RecordValidator {

	public static ValidatorResponse isValid(MarcRecord record) {
		return new ValidatorResponse();
	}
}
