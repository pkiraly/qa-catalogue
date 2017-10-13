package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.definition.controlsubfields.leader.*;

import java.util.*;

public class LeaderSubfields {

	private static final List<ControlSubfield> subfields = new ArrayList<>();
	private static final Map<String, ControlSubfield> subfieldLabelMap = new HashMap<>();
	private static final Map<String, ControlSubfield> subfieldIdMap = new HashMap<>();

	static {

		addAllSubfields(
			Arrays.asList(
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
			)
		);
	}

	private static void addAllSubfields(List<ControlSubfield> _subfields) {
		subfields.addAll(_subfields);
		for (ControlSubfield subField : _subfields) {
			subfieldLabelMap.put(subField.getLabel(), subField);
			subfieldIdMap.put(subField.getId(), subField);
		}
	}

	public static List<ControlSubfield> getSubfields() {
		return subfields;
	}

	public static ControlSubfield getByLabel(String key) {
		return subfieldLabelMap.get(key);
	}

	public static ControlSubfield getById(String key) {
		return subfieldIdMap.get(key);
	}

}
