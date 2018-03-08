package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Color
 * https://www.loc.gov/marc/bibliographic/bd007a.html
 */
public class Tag007map03 extends ControlSubfieldDefinition {
	private static Tag007map03 uniqueInstance;

	private Tag007map03() {
		initialize();
		extractValidCodes();
	}

	public static Tag007map03 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007map03();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Color";
		id = "tag007map03";
		mqTag = "color";
		positionStart = 3;
		positionEnd = 4;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007a.html";
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