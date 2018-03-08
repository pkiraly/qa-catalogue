package de.gwdg.metadataqa.marc.definition.controlsubfields;

import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.controltype.ControlType;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ControlSubfieldList {

	static Map<? extends ControlType, List<ControlSubfieldDefinition>> subfields = new TreeMap<>();

	public static Map<? extends ControlType, List<ControlSubfieldDefinition>> getSubfields() {
		return subfields;
	}

	public static <T extends ControlType> List<ControlSubfieldDefinition> get(T category) {
		return subfields.get(category);
	}
}
