package de.gwdg.metadataqa.marc.definition.general.parser;

import java.util.Map;

public interface SubfieldContentParser {

	public Map<String, String> parse(String content);
}
