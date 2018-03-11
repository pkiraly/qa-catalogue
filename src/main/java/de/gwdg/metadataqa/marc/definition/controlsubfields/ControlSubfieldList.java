package de.gwdg.metadataqa.marc.definition.controlsubfields;

import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.controltype.ControlType;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ControlSubfieldList {

	protected Map<String, List<ControlSubfieldDefinition>> subfields = new TreeMap<>();

	public Map<String, List<ControlSubfieldDefinition>> getSubfields() {
		return subfields;
	}

	public List<ControlSubfieldDefinition> get(String category) {
		return subfields.get(category);
	}
}
