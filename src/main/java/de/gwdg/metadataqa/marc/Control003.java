package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.ControlSubfield;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;

import java.util.*;
import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control003 extends ControlField implements Extractable, Validatable {

	private static final Logger logger = Logger.getLogger(Control003.class.getCanonicalName());

	private String content;

	private Map<ControlSubfield, String> valuesMap;
	private Map<Integer, ControlSubfield> byPosition = new LinkedHashMap<>();
	private static final String tag = "003";
	private static final String label = "Control Number Identifier";
	private static final String mqTag = "ControlNumberIdentifier";

	public Control003(String content) {
		this.content = content;
		valuesMap = new LinkedHashMap<>();
		process();
	}

	private void process() {
	}

	public String resolve(ControlSubfield key) {
		String value = (String)valuesMap.get(key);
		String text = key.resolve(value);
		return text;
	}

	public String getContent() {
		return content;
	}

	public Map<ControlSubfield, String> getMap() {
		return valuesMap;
	}

	public String getValueByPosition(int position) {
		return valuesMap.get(getSubfieldByPosition(position));
	}

	public ControlSubfield getSubfieldByPosition(int position) {
		return byPosition.get(position);
	}

	public Set<Integer> getSubfieldPositions() {
		return byPosition.keySet();
	}

	public static String getLabel() {
		return label;
	}

	public static String getMqTag() {
		return mqTag;
	}

	@Override
	public String toString() {
		return "Control007{" +
				"content='" + content + '\'' +
				", map=" + valuesMap +
				'}';
	}

	@Override
	public Map<String, List<String>> getKeyValuePairs() {
		return getKeyValuePairs(false);
	}

	public Map<String, List<String>> getKeyValuePairs(boolean withMarcTags) {
		Map<String, List<String>> map = new LinkedHashMap<>();
		String key = withMarcTags ? String.format("%s_%s", tag, mqTag) : mqTag;
		map.put(key, Arrays.asList(content));
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