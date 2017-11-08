package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.ControlSubfield;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.ValidationError;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control005  extends ControlField implements Extractable, Validatable {

	private static final Logger logger = Logger.getLogger(Control005.class.getCanonicalName());
	private static final Pattern DATE_TIME = Pattern.compile("^(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})\\.(\\d)$");

	private String content;
	private Map<ControlSubfield, String> valuesMap;
	private Map<Integer, ControlSubfield> byPosition = new LinkedHashMap<>();
	private static final String label = "Date and Time of Latest Transaction";
	private static final String mqTag = "LatestTransactionTime";

	public Control005(String content) {
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