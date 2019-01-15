package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.utils.keygenerator.PositionalControlFieldKeyGenerator;

import java.util.*;

public class MarcPositionalControlField extends MarcControlField {

	protected ControlFieldDefinition definition;
	protected MarcRecord marcRecord;
	protected Map<ControlSubfieldDefinition, String> valuesMap;
	protected List<ControlValue> valuesList;
	protected Leader.Type recordType;

	public MarcPositionalControlField(ControlFieldDefinition definition, String content) {
		this(definition, content, null);
	}

	public MarcPositionalControlField(ControlFieldDefinition definition,
												 String content,
												 Leader.Type recordType) {
		super(definition, content);
		this.definition = definition;
		this.recordType = recordType;
		valuesMap = new LinkedHashMap<>();
		valuesList = new ArrayList<>();
	}

	public void setMarcRecord(MarcRecord record) {
		this.marcRecord = record;
		for (ControlValue value : valuesList) {
			value.setRecord(marcRecord);
		}
	}

	protected void processContent() {}

	public Map<String, List<String>> getKeyValuePairs(SolrFieldType type) {
		return getKeyValuePairs(definition.getTag(), definition.getMqTag(), type);
	}

	public Map<String, List<String>> getKeyValuePairs(String tag,
																	  String mqTag,
																	  SolrFieldType type) {
		Map<String, List<String>> map = new LinkedHashMap<>();
		PositionalControlFieldKeyGenerator keyGenerator =
			new PositionalControlFieldKeyGenerator(tag, mqTag, type);
		if (content != null) {
			map.put(keyGenerator.forTag(), Arrays.asList(content));
			for (Map.Entry<ControlSubfieldDefinition, String> entry : valuesMap.entrySet()) {
				ControlSubfieldDefinition controlSubfield = entry.getKey();
				String value = controlSubfield.resolve(entry.getValue());
				map.put(keyGenerator.forSubfield(controlSubfield), Arrays.asList(value));
			}
		}
		return map;
	}

	public Map<ControlSubfieldDefinition, String> getMap() {
		return valuesMap;
	}

	public List<ControlValue> getValuesList() {
		return valuesList;
	}

	public String getLabel() {
		return definition.getLabel();
	}

	public String getTag() {
		return definition.getTag();
	}

	public String getMqTag() {
		return definition.getMqTag();
	}

	public Cardinality getCardinality() {
		return definition.getCardinality();
	}

	@Override
	public boolean validate(MarcVersion marcVersion) {
		boolean isValid = true;
		errors = new ArrayList<>();
		validationErrors = new ArrayList<>();
		for (ControlValue controlValue : valuesList) {
			if (!controlValue.validate(marcVersion)) {
				errors.addAll(controlValue.getErrors());
				validationErrors.addAll(controlValue.getValidationErrors());
				isValid = false;
			}
		}
		return isValid;
	}

	@Override
	public List<String> getErrors() {
		return errors;
	}

	@Override
	public List<ValidationError> getValidationErrors() {
		return validationErrors;
	}
}
