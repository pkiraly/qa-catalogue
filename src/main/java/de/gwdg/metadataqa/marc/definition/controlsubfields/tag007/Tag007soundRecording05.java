package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Groove width/groove pitch
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording05 extends ControlSubfieldDefinition {
	private static Tag007soundRecording05 uniqueInstance;

	private Tag007soundRecording05() {
		initialize();
		extractValidCodes();
	}

	public static Tag007soundRecording05 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007soundRecording05();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Groove width/groove pitch";
		id = "tag007soundRecording05";
		mqTag = "grooveWidthOrGroovePitch";
		positionStart = 5;
		positionEnd = 6;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
		codes = Utils.generateCodes(
			"m", "Microgroove/fine",
			"n", "Not applicable",
			"s", "Coarse/standard",
			"u", "Unknown",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}