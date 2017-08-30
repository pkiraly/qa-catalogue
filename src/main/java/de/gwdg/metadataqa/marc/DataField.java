package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;

import java.util.*;

public class DataField {
	private DataFieldDefinition definition;
	private String tag;
	private String ind1;
	private String ind2;
	private List<MarcSubfield> subfields;
	private Map<String, List<MarcSubfield>> subfieldIndex = new LinkedHashMap<>();

	public <T extends DataFieldDefinition> DataField(T definition, String ind1, String ind2, String... subfields) {
		this.definition = definition;
		this.ind1 = ind1;
		this.ind2 = ind2;
		this.subfields = new ArrayList<>();
		if (subfields != null) {
			parseSubfieldArray(definition, subfields);
		}
	}

	private <T extends DataFieldDefinition> void parseSubfieldArray(T definition, String[] subfields) {
		for (int i = 0; i < subfields.length; i += 2) {
			String code = subfields[i];
			String value = subfields[i + 1];
			SubfieldDefinition subfieldDefinition = definition.getSubfield(code);
			MarcSubfield subfield = new MarcSubfield(subfieldDefinition, code, value);
			this.subfields.add(subfield);
			if (!subfieldIndex.containsKey(code))
				subfieldIndex.put(code, new LinkedList<>());
			subfieldIndex.get(code).add(subfield);
		}
	}

	public Map<String, List<String>> getHumanReadableMap() {
		Map<String, List<String>> map = new LinkedHashMap<>();
		if (!definition.getInd1().getLabel().equals(""))
			map.put(definition.getInd1().getLabel(), Arrays.asList(resolveInd1()));
		if (!definition.getInd2().getLabel().equals(""))
			map.put(definition.getInd2().getLabel(), Arrays.asList(resolveInd2()));
		for (MarcSubfield subfield : subfields) {
			if (!map.containsKey(subfield.getLabel()))
				map.put(subfield.getLabel(), new ArrayList<>());
			map.get(subfield.getLabel()).add(subfield.resolve());
		}
		return map;
	}

	public String resolveInd1() {
		if (definition.getInd1().getLabel().equals(""))
			return "";

		if (!definition.getInd1().hasCode(ind1))
			return ind1;

		return definition.getInd1().getCode(ind1).getLabel();
	}

	public String resolveInd2() {
		if (definition.getInd2().getLabel().equals(""))
			return "";

		if (!definition.getInd2().hasCode(ind2))
			return ind2;

		return definition.getInd2().getCode(ind2).getLabel();
	}

	public String getTag() {
		return definition.getTag();
	}

	public String getInd1() {
		return ind1;
	}

	public String getInd2() {
		return ind2;
	}

	public List<MarcSubfield> getSubfield(String code) {
		return subfieldIndex.getOrDefault(code, null);
	}
}
