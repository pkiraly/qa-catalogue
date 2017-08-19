package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.Control008;

public enum Control008Type {

	ALL_MATERIALS("All Materials"),
	BOOKS("Books"),
	CONTINUING_RESOURCES("Continuing Resources"),
	MUSIC("Music"),
	MAPS("Maps"),
	VISUAL_MATERIALS("Visual Materials"),
	COMPUTER_FILES("Computer Files"),
	MIXED_MATERIALS("Mixed Materials");

	String value;
	Control008Type(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static Control008Type byCode(String value) {
		for(Control008Type type : values())
			if (type.value.equals(value))
				return type;
		return null;
	}
}
