package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Kind of disc, cylinder, or tape
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording09 extends ControlSubfieldDefinition {
	private static Tag007soundRecording09 uniqueInstance;

	private Tag007soundRecording09() {
		initialize();
		extractValidCodes();
	}

	public static Tag007soundRecording09 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007soundRecording09();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Kind of disc, cylinder, or tape";
		id = "tag007soundRecording09";
		mqTag = "kindOfDiscCylinderOrTape";
		positionStart = 9;
		positionEnd = 10;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
		codes = Utils.generateCodes(
			"a", "Master tape",
			"b", "Tape duplication master",
			"d", "Disc master (negative)",
			"i", "Instantaneous (recorded on the spot)",
			"m", "Mass-produced",
			"n", "Not applicable",
			"r", "Mother (positive)",
			"s", "Stamper (negative)",
			"t", "Test pressing",
			"u", "Unknown",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}