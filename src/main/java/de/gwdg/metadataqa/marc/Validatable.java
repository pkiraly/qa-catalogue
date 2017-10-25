package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.MarcVersion;

import java.util.List;

public interface Validatable {

	public boolean validate(MarcVersion marcVersion);
	public List<String> getErrors();

}
