package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.ControlSubfield;
import de.gwdg.metadataqa.marc.definition.ControlValue;

import java.util.*;

public class PositionalControlField extends ControlField implements Extractable, Validatable  {

	protected static String mqTag;
	protected String content;
	protected Map<ControlSubfield, String> valuesMap;
	protected List<ControlValue> valuesList;
	protected List<String> errors;

	@Override
	public boolean validate() {
		boolean isValid = true;
		errors = new ArrayList<>();
		for (ControlValue controlValue : valuesList) {
			if (!controlValue.validate()) {
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

	@Override
	public Map<String, List<String>> getKeyValuePairs() {
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
