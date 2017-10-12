package de.gwdg.metadataqa.marc.definition.controlsubfields;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Form of item
 * https://www.loc.gov/marc/bibliographic/bd008m.html
 */
public class Tag008music23 extends ControlSubfield {
	private static Tag008music23 uniqueInstance;

	private Tag008music23() {
		initialize();
		extractValidCodes();
	}

	public static Tag008music23 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008music23();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Form of item";
		id = "tag008music23";
		mqTag = "formOfItem";
		positionStart = 23;
		positionEnd = 24;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008m.html";
		codes = Utils.generateCodes(
			" ", "None of the following",
			"a", "Microfilm",
			"b", "Microfiche",
			"c", "Microopaque",
			"d", "Large print",
			"f", "Braille",
			"o", "Online",
			"q", "Direct electronic",
			"r", "Regular print reproduction",
			"s", "Electronic",
			"|", "No attempt to code"
		);
	}
}