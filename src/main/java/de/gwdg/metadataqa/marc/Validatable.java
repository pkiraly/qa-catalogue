package de.gwdg.metadataqa.marc;

import java.util.List;

public interface Validatable {

	public boolean validate();
	public List<String> getErrors();

}
