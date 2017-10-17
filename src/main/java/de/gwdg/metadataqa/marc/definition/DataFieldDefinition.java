package de.gwdg.metadataqa.marc.definition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class DataFieldDefinition {
	protected String tag;
	protected String bibframeTag;
	protected String mqTag;
	protected String label;
	protected Cardinality cardinality = Cardinality.Nonrepeatable;
	protected Indicator ind1;
	protected Indicator ind2;
	protected List<SubfieldDefinition> subfields;
	protected Map<String, SubfieldDefinition> subfieldIndex = new LinkedHashMap<>();
	protected String indexTag = null;

	public String getTag() {
		return tag;
	}

	public String getIndexTag() {
		if (indexTag == null) {
			if (mqTag != null)
				indexTag = mqTag;
			else if (bibframeTag != null)
				indexTag = bibframeTag.replace("/", "");
			else
				indexTag = tag;
		}
		return indexTag;
	}

	public String getLabel() {
		return label;
	}

	public Cardinality getCardinality() {
		return cardinality;
	}

	public Indicator getInd1() {
		return ind1;
	}

	public Indicator getInd2() {
		return ind2;
	}

	public List<SubfieldDefinition> getSubfields() {
		return subfields;
	}

	protected void setSubfields(String... input) {
		subfields = new ArrayList<>();
		for (int i = 0; i < input.length; i += 2) {
			subfields.add(new SubfieldDefinition(input[i], input[i + 1]));
		}
		indexSubfields();
	}

	protected void setSubfieldsWithCardinality(String... input) {
		subfields = new ArrayList<>();
		for (int i = 0; i < input.length; i += 3) {
			String code = input[i];
			String label = input[i + 1];
			String cardinality = input[i + 2];
			SubfieldDefinition definition = new SubfieldDefinition(code, label, cardinality);
			definition.setParent(this);
			subfields.add(definition);
		}
		indexSubfields();
	}

	protected void indexSubfields() {
		for (SubfieldDefinition subfield : subfields) {
			subfieldIndex.put(subfield.getCode(), subfield);
		}
	}

	/**
	 *
	 * @param code
	 * @return The subfield definition or null
	 */
	public SubfieldDefinition getSubfield(String code) {
		return subfieldIndex.getOrDefault(code, null);
	}
}
