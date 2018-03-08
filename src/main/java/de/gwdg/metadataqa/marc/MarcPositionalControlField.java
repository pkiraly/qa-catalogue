package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.ControlValue;
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
		super();
		this.definition = definition;
		this.content = content;
		this.recordType = recordType;
		valuesMap = new LinkedHashMap<>();
		valuesList = new ArrayList<>();
		// process();
	}

	public void setMarcRecord(MarcRecord record) {
		this.marcRecord = record;
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
			for (ControlSubfieldDefinition controlSubfield : valuesMap.keySet()) {
				String value = controlSubfield.resolve(valuesMap.get(controlSubfield));
				map.put(keyGenerator.forSubfield(controlSubfield), Arrays.asList(value));
			}
		}
		return map;
	}

	public Map<ControlSubfieldDefinition, String> getMap() {
		return valuesMap;
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
}
