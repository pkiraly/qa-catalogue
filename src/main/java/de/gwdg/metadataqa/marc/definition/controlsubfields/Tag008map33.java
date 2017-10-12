package de.gwdg.metadataqa.marc.definition.controlsubfields;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Special format characteristics
 * https://www.loc.gov/marc/bibliographic/bd008p.html
 */
public class Tag008map33 extends ControlSubfield {
	private static Tag008map33 uniqueInstance;

	private Tag008map33() {
		initialize();
		extractValidCodes();
	}

	public static Tag008map33 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008map33();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Special format characteristics";
		id = "tag008map33";
		mqTag = "specialFormatCharacteristics";
		positionStart = 33;
		positionEnd = 35;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008p.html";
		codes = Utils.generateCodes(
			" ", "No specified special format characteristics",
			"e", "Manuscript",
			"j", "Picture card, post card",
			"k", "Calendar",
			"l", "Puzzle",
			"n", "Game",
			"o", "Wall map",
			"p", "Playing cards",
			"r", "Loose-leaf",
			"z", "Other",
			"||", "No attempt to code"
		);
		repeatableContent = true;
		unitLength = 1;
	}
}