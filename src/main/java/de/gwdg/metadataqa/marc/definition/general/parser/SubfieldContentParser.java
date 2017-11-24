package de.gwdg.metadataqa.marc.definition.general.parser;

import java.util.Map;

public interface SubfieldContentParser {

	Map<String, String> parse(String content) throws ParserException;
}
