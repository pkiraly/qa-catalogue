package de.gwdg.metadataqa.marc.definition.controlsubfields;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Form of original item
 * https://www.loc.gov/marc/bibliographic/bd008s.html
 */
public class Tag008continuing22 extends ControlSubfield {
	private static Tag008continuing22 uniqueInstance;

	private Tag008continuing22() {
		initialize();
		extractValidCodes();
	}

	public static Tag008continuing22 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008continuing22();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Form of original item";
		id = "tag008continuing22";
		mqTag = "formOfOriginalItem";
		positionStart = 22;
		positionEnd = 23;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008s.html";
		codes = Utils.generateCodes(
			" ", "None of the following",
			"a", "Microfilm",
			"b", "Microfiche",
			"c", "Microopaque",
			"d", "Large print",
			"e", "Newspaper format",
			"f", "Braille",
			"o", "Online",
			"q", "Direct electronic",
			"s", "Electronic",
			"|", "No attempt to code"
		);
	}
}