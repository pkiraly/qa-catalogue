package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MarcControlField implements Validatable, Extractable {

	protected DataFieldDefinition definition;
	protected String content;

	protected List<String> errors;
	protected List<ValidationError> validationErrors;

	public MarcControlField() {
	}

	public MarcControlField(DataFieldDefinition definition, String content) {
		this.definition = definition;
		this.content = content;
	}

	public DataFieldDefinition getDefinition() {
		return definition;
	}

	public String getContent() {
		return content;
	}

	public String getSolrKey(SolrFieldType type, String tag, String mqTag) {
		String key = tag;
		switch (type) {
			case HUMAN: key = mqTag; break;
			case MIXED: key = String.format("%s_%s", tag, mqTag); break;
			case MARC:
			default:  key = tag; break;
		}
		return key;
	}

	@Override
	public Map<String, List<String>> getKeyValuePairs() {
		return getKeyValuePairs(SolrFieldType.MARC);
	}

	@Override
	public Map<String, List<String>> getKeyValuePairs(SolrFieldType type) {
		Map<String, List<String>> map = new LinkedHashMap<>();
		map.put(
			getSolrKey(type, definition.getTag(), definition.getMqTag()),
			Arrays.asList(content));
		return map;
	}

	@Override
	public boolean validate(MarcVersion marcVersion) {
		return true;
	}

	@Override
	public List<String> getErrors() {
		return null;
	}

	@Override
	public List<ValidationError> getValidationErrors() {
		return null;
	}
}
