package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.ControlSubfield;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.ValidationError;

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

	public Map<String, List<String>> getKeyValuePairs(String mqTag) {
		Map<String, List<String>> map = new LinkedHashMap<>();
		if (content != null) {
			map.put(mqTag, Arrays.asList(content));
			for (ControlSubfield controlSubfield : valuesMap.keySet()) {
				String code = controlSubfield.getMqTag() != null
					? controlSubfield.getMqTag()
					: controlSubfield.getId();
				String key = String.format("%s_%s", mqTag, code);
				String value = controlSubfield.resolve(valuesMap.get(controlSubfield));
				map.put(key, Arrays.asList(value));
			}
		}
		return map;
	}

	public Map<ControlSubfield, String> getMap() {
		return valuesMap;
	}
}
