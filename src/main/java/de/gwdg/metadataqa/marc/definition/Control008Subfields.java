package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.definition.controlsubfields.tag008.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Control008Subfields {

	private static final Map<Control008Type, List<ControlSubfield>> subfields = new HashMap<>();

	static {
		subfields.put(Control008Type.ALL_MATERIALS, Arrays.asList(
			Tag008all00.getInstance(),
			Tag008all06.getInstance(),
			Tag008all07.getInstance(),
			Tag008all11.getInstance(),
			Tag008all15.getInstance(),
			Tag008all35.getInstance(),
			Tag008all38.getInstance(),
			Tag008all39.getInstance()
		));
		subfields.put(Control008Type.BOOKS, Arrays.asList(
			Tag008book18.getInstance(),
			Tag008book22.getInstance(),
			Tag008book23.getInstance(),
			Tag008book24.getInstance(),
			Tag008book28.getInstance(),
			Tag008book29.getInstance(),
			Tag008book30.getInstance(),
			Tag008book31.getInstance(),
			// new ControlSubfield("undefined", 32, 33),
			Tag008book33.getInstance(),
			Tag008book34.getInstance()
		));
		subfields.put(Control008Type.COMPUTER_FILES, Arrays.asList(
			// new ControlSubfield("undefined", 18, 21),
			Tag008computer22.getInstance(),
			Tag008computer23.getInstance(),
			// new ControlSubfield("undefined", 24, 25),
			Tag008computer26.getInstance(),
			// new ControlSubfield("undefined", 27, 28),
			Tag008computer28.getInstance()
			// new ControlSubfield("undefined", 29, 35)
		));
		subfields.put(Control008Type.MAPS, Arrays.asList(
			Tag008map18.getInstance(),
			Tag008map22.getInstance(),
			// new ControlSubfield("undefined", 24, 25),
			Tag008map25.getInstance(),
			// new ControlSubfield("undefined", 26, 28),
			Tag008map28.getInstance(),
			Tag008map29.getInstance(),
			// new ControlSubfield("undefined", 30, 31),
			Tag008map31.getInstance(),
			// new ControlSubfield("undefined", 32, 33)
			Tag008map33.getInstance()
		));
		subfields.put(Control008Type.MUSIC, Arrays.asList(
			Tag008music18.getInstance(),
			Tag008music20.getInstance(),
			Tag008music21.getInstance(),
			Tag008music22.getInstance(),
			Tag008music23.getInstance(),
			Tag008music24.getInstance(),
			Tag008music30.getInstance(),
			// new ControlSubfield("Undefined", 32, 33),
			Tag008music33.getInstance()
			// new ControlSubfield("Undefined", 34, 35)
		));
		subfields.put(Control008Type.CONTINUING_RESOURCES, Arrays.asList(
			Tag008continuing18.getInstance(),
			Tag008continuing19.getInstance(),
			// new ControlSubfield("Undefined", 20, 21),
			Tag008continuing21.getInstance(),
			Tag008continuing22.getInstance(),
			Tag008continuing23.getInstance(),
			Tag008continuing24.getInstance(),
			Tag008continuing25.getInstance(),
			Tag008continuing28.getInstance(),
			Tag008continuing29.getInstance(),
			// new ControlSubfield("Undefined", 30, 33),
			Tag008continuing33.getInstance(),
			Tag008continuing34.getInstance()
		));
		subfields.put(Control008Type.VISUAL_MATERIALS, Arrays.asList(
			Tag008visual18.getInstance(),
			// new ControlSubfield("Undefined", 21, 22),
			Tag008visual22.getInstance(),
			// new ControlSubfield("Undefined", 23, 28),
			Tag008visual28.getInstance(),
			Tag008visual29.getInstance(),
			// new ControlSubfield("Undefined", 30, 33),
			Tag008visual33.getInstance(),
			Tag008visual34.getInstance()
		));
		subfields.put(Control008Type.MIXED_MATERIALS, Arrays.asList(
			// new ControlSubfield("Undefined", 18, 23),
			Tag008mixed23.getInstance()
			// new ControlSubfield("Undefined", 24, 35),
		));
	}

	public static Map<Control008Type, List<ControlSubfield>> getSubfields() {
		return subfields;
	}

	public static List<ControlSubfield> get(Control008Type category) {
		return subfields.get(category);
	}
}
