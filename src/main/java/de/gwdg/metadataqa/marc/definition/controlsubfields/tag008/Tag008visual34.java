package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Technique
 * https://www.loc.gov/marc/bibliographic/bd008v.html
 */
public class Tag008visual34 extends ControlSubfieldDefinition {
	private static Tag008visual34 uniqueInstance;

	private Tag008visual34() {
		initialize();
		extractValidCodes();
	}

	public static Tag008visual34 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008visual34();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Technique";
		id = "tag008visual34";
		mqTag = "technique";
		positionStart = 34;
		positionEnd = 35;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008v.html";
		codes = Utils.generateCodes(
			"a", "Animation",
			"c", "Animation and live action",
			"l", "Live action",
			"n", "Not applicable",
			"u", "Unknown",
			"z", "Other",
			"|", "No attempt to code"
		);
		historicalCodes = Utils.generateCodes(
			" ", "Not applicable [OBSOLETE, 1980]"
		);
	}
}