package de.gwdg.metadataqa.marc.definition;

import java.util.List;

public interface Validator {
	public boolean isValid(String value);
	public List<String> getErrors();
}
