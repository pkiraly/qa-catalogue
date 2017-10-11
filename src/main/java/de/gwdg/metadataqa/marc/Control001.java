package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.Control007Category;
import de.gwdg.metadataqa.marc.definition.Control007Subfields;
import de.gwdg.metadataqa.marc.definition.ControlValue;

import java.util.*;
import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control001 implements Extractable, Validatable {

	private static final Logger logger = Logger.getLogger(Control001.class.getCanonicalName());

	private String content;

	private Map<ControlSubfield, String> valuesMap;
	private Map<Integer, ControlSubfield> byPosition = new LinkedHashMap<>();
	private static final String label = "Control Number";
	private static final String mqTag = "ControlNumber";

	public Control001(String content) {
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
		Map<String, List<String>> map = new LinkedHashMap<>();
		map.put(mqTag, Arrays.asList(content));
		return map;
	}

	@Override
	public boolean validate() {
		return false;
	}

	@Override
	public List<String> getErrors() {
		return null;
	}

}