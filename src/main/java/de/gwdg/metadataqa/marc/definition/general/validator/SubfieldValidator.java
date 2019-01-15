package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;

import java.io.Serializable;

public interface SubfieldValidator {
	ValidatorResponse isValid(MarcSubfield subfield);
}
