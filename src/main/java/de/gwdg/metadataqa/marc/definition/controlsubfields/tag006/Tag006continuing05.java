package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Form of original item
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006continuing05 extends ControlSubfield {
	private static Tag006continuing05 uniqueInstance;

	private Tag006continuing05() {
		initialize();
		extractValidCodes();
	}

	public static Tag006continuing05 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag006continuing05();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Form of original item";
		id = "tag006continuing05";
		mqTag = "formOfOriginalItem";
		positionStart = 5;
		positionEnd = 6;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
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