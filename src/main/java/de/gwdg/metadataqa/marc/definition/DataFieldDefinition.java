package de.gwdg.metadataqa.marc.definition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataFieldDefinition {
	protected String tag;
	protected String label;
	protected Cardinality cardinality = Cardinality.Nonrepeatable;
	protected Indicator ind1;
	protected Indicator ind2;
	protected List<SubfieldDefinition> subfields;
	protected Map<String, SubfieldDefinition> subfieldIndex = new LinkedHashMap<>();

	public String getTag() {
		return tag;
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
			subfields.add(new SubfieldDefinition(input[i], input[i + 1], input[i + 2]));
		}
		indexSubfields();
	}

	private void indexSubfields() {
		for (SubfieldDefinition subfield : subfields) {
			subfieldIndex.put(subfield.getCode(), subfield);
		}
	}

	public SubfieldDefinition getSubfield(String code) {
		return subfieldIndex.getOrDefault(code, null);
	}
}
