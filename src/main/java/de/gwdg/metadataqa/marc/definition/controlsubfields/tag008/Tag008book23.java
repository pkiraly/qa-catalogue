package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Form of item
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag008book23 extends ControlSubfield {
	private static Tag008book23 uniqueInstance;

	private Tag008book23() {
		initialize();
		extractValidCodes();
	}

	public static Tag008book23 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008book23();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Form of item";
		id = "tag008book23";
		mqTag = "formOfItem";
		positionStart = 23;
		positionEnd = 24;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008b.html";
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