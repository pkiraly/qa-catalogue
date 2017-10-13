package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Form of item
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006book06 extends ControlSubfield {
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
			"f", "Braille"
		);
	}
}