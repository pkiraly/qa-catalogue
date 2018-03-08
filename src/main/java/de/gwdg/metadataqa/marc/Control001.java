package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.ControlField;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;

import java.util.*;
import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control001 extends MarcControlField implements Extractable {

	private static final Logger logger = Logger.getLogger(Control001.class.getCanonicalName());

	private String content;

	private Map<ControlSubfieldDefinition, String> valuesMap;
	private Map<Integer, ControlSubfieldDefinition> byPosition = new LinkedHashMap<>();
	private static final String tag = "001";
	private static final String label = "Control Number";
	private static final String mqTag = "ControlNumber";
	private static final Cardinality cardinality = Cardinality.Nonrepeatable;

	public Control001(String content) {
		this.content = content;
		valuesMap = new LinkedHashMap<>();
		process();
	}

	private void process() {
	}

	public String resolve(ControlSubfieldDefinition key) {
		String value = (String)valuesMap.get(key);
		String text = key.resolve(value);
		return text;
	}

	public String getContent() {
		return content;
	}

	public Map<ControlSubfieldDefinition, String> getMap() {
		return valuesMap;
	}

	public String getValueByPosition(int position) {
		return valuesMap.get(getSubfieldByPosition(position));
	}

	public ControlSubfieldDefinition getSubfieldByPosition(int position) {
		return byPosition.get(position);
	}

	public Set<Integer> getSubfieldPositions() {
		return byPosition.keySet();
	}

	public static String getLabel() {
		return label;
	}

	public static String getTag() {
		return mqTag;
	}

	public static String getMqTag() {
		return mqTag;
	}

	public static Cardinality getCardinality() {
		return cardinality;
	}

	@Override
	public String toString() {
		return "Control001{" +
				"content='" + content + '\'' +
				", map=" + valuesMap +
				'}';
	}

	@Override
	public Map<String, List<String>> getKeyValuePairs() {
		return getKeyValuePairs(SolrFieldType.MARC);
	}

	public Map<String, List<String>> getKeyValuePairs(SolrFieldType type) {
		Map<String, List<String>> map = new LinkedHashMap<>();
		map.put(getSolrKey(type, tag, mqTag), Arrays.asList(content));
		return map;
	}
}