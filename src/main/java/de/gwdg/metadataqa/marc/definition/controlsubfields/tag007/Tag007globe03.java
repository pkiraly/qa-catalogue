package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Color
 * https://www.loc.gov/marc/bibliographic/bd007d.html
 */
public class Tag007globe03 extends ControlSubfieldDefinition {
	private static Tag007globe03 uniqueInstance;

	private Tag007globe03() {
		initialize();
		extractValidCodes();
	}

	public static Tag007globe03 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007globe03();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Color";
		id = "tag007globe03";
		mqTag = "color";
		positionStart = 3;
		positionEnd = 4;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007d.html";
		codes = Utils.generateCodes(
			"a", "One color",
			"c", "Multicolored",
			"|", "No attempt to code"
		);
		historicalCodes = Utils.generateCodes(
			"b", "Multicolored [OBSOLETE, 1982]"
		);
	}
}