package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Special format characteristics
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006map16 extends ControlSubfieldDefinition {
	private static Tag006map16 uniqueInstance;

	private Tag006map16() {
		initialize();
		extractValidCodes();
	}

	public static Tag006map16 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag006map16();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Special format characteristics";
		id = "tag006map16";
		mqTag = "specialFormatCharacteristics";
		positionStart = 16;
		positionEnd = 18;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
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
			"|", "No attempt to code"
		);
		repeatableContent = true;
		unitLength = 1;

	}
}