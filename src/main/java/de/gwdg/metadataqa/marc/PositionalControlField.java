package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.ControlSubfield;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.MarcVersion;

import java.util.*;

public abstract class PositionalControlField extends ControlField implements Extractable, Validatable  {

	protected String content;
	protected Map<ControlSubfield, String> valuesMap;
	protected List<ControlValue> valuesList;
	protected List<String> errors;

	@Override
	public boolean validate(MarcVersion marcVersion) {
		boolean isValid = true;
		errors = new ArrayList<>();
		for (ControlValue controlValue : valuesList) {
			if (!controlValue.validate(marcVersion)) {
				errors.addAll(controlValue.getErrors());
				isValid = false;
			}
		}
		return isValid;
	}

	@Override
	public List<String> getErrors() {
		return errors;
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
}
