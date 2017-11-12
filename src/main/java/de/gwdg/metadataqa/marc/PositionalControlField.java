package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.ControlSubfield;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.utils.keygenerator.PositionalControlFieldKeyGenerator;

import java.util.*;

public abstract class PositionalControlField extends ControlField implements Extractable, Validatable  {

	protected MarcRecord marcRecord;
	protected Map<ControlSubfield, String> valuesMap;
	protected List<ControlValue> valuesList;
	protected List<String> errors;
	protected List<ValidationError> validationErrors;

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

	public void setMarcRecord(MarcRecord marcRecord) {
		this.marcRecord = marcRecord;
		for (ControlValue value : valuesList) {
			value.setRecord(marcRecord);
		}
	}

	@Override
	public List<String> getErrors() {
		return errors;
	}

	@Override
	public List<ValidationError> getValidationErrors() {
		return validationErrors;
	}

	@Override
	public Map<String, List<String>> getKeyValuePairs() {
		return getKeyValuePairs(SolrFieldType.MARC);
	}

	public Map<String, List<String>> getKeyValuePairs(
			String tag, String mqTag, SolrFieldType type) {
		Map<String, List<String>> map = new LinkedHashMap<>();
		PositionalControlFieldKeyGenerator keyGenerator = new PositionalControlFieldKeyGenerator(
			tag, mqTag, type);
		if (content != null) {
			map.put(keyGenerator.forTag(), Arrays.asList(content));
			for (ControlSubfield controlSubfield : valuesMap.keySet()) {
				String value = controlSubfield.resolve(valuesMap.get(controlSubfield));
				map.put(keyGenerator.forSubfield(controlSubfield), Arrays.asList(value));
			}
		}
		return map;
	}

	public Map<ControlSubfield, String> getMap() {
		return valuesMap;
	}
}
