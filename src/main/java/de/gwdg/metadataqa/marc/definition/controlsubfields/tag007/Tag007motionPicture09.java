package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Production elements
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture09 extends ControlSubfieldDefinition {
	private static Tag007motionPicture09 uniqueInstance;

	private Tag007motionPicture09() {
		initialize();
		extractValidCodes();
	}

	public static Tag007motionPicture09 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007motionPicture09();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Production elements";
		id = "tag007motionPicture09";
		mqTag = "productionElements";
		positionStart = 9;
		positionEnd = 10;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
		codes = Utils.generateCodes(
			"a", "Workprint",
			"b", "Trims",
			"c", "Outtakes",
			"d", "Rushes",
			"e", "Mixing tracks",
			"f", "Title bands/inter-title rolls",
			"g", "Production rolls",
			"n", "Not applicable",
			"z", "Other",
			"|", "No attempt to code"
		);
		historicalCodes = Utils.generateCodes(
			"h", "Other [OBSOLETE, 1988]"
		);
	}
}