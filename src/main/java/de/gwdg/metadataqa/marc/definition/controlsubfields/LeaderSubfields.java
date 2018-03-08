package de.gwdg.metadataqa.marc.definition.controlsubfields;

import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.controlsubfields.leader.*;
import de.gwdg.metadataqa.marc.definition.controltype.Control008Type;

import java.util.*;

public class LeaderSubfields extends ControlSubfieldList {

	private static Map<Control008Type, List<ControlSubfieldDefinition>> subfields = new TreeMap<>();

	private static List<ControlSubfieldDefinition> subfieldList = new ArrayList<>();

	private static final Map<String, ControlSubfieldDefinition> subfieldLabelMap = new HashMap<>();
	private static final Map<String, ControlSubfieldDefinition> subfieldIdMap = new HashMap<>();

	static {

		// subfieldList.put(Control008Type.ALL_MATERIALS, Arrays.asList());

		subfieldList = Arrays.asList(
			Leader00.getInstance(),
			Leader05.getInstance(),
			Leader06.getInstance(),
			Leader07.getInstance(),
			Leader08.getInstance(),
			Leader09.getInstance(),
			Leader10.getInstance(),
			Leader11.getInstance(),
			Leader12.getInstance(),
			Leader17.getInstance(),
			Leader18.getInstance(),
			Leader19.getInstance(),
			Leader20.getInstance(),
			Leader21.getInstance(),
			Leader22.getInstance()
			// new ControlSubField("undefined", 23, 24)
		);
		addAllSubfields(subfieldList);
		subfields.put(Control008Type.ALL_MATERIALS, subfieldList);
	}

	private static void addAllSubfields(List<ControlSubfieldDefinition> _subfields) {
		for (ControlSubfieldDefinition subField : _subfields) {
			subfieldLabelMap.put(subField.getLabel(), subField);
			subfieldIdMap.put(subField.getId(), subField);
		}
	}

	public static List<ControlSubfieldDefinition> getSubfieldList() {
		return subfieldList;
	}

	public static ControlSubfieldDefinition getByLabel(String key) {
		return subfieldLabelMap.get(key);
	}

	public static ControlSubfieldDefinition getById(String key) {
		return subfieldIdMap.get(key);
	}

}
