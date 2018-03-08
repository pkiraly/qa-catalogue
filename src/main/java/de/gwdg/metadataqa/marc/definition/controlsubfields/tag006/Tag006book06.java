package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Form of item
 * same as 008/23
 * https://www.loc.gov/marc/bibliographic/bd006.html
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag006book06 extends ControlSubfieldDefinition {
	private static Tag006book06 uniqueInstance;

	private Tag006book06() {
		initialize();
		extractValidCodes();
	}

	public static Tag006book06 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag006book06();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Form of item";
		id = "tag006book06";
		mqTag = "formOfItem";
		positionStart = 6;
		positionEnd = 7;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
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