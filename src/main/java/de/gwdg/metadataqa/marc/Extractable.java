package de.gwdg.metadataqa.marc;

import java.util.List;
import java.util.Map;

public interface Extractable {

	public Map<String, List<String>> getKeyValuePairs();
}
