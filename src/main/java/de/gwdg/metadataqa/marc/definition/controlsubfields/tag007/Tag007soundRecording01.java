package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Specific material designation
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording01 extends ControlSubfield {
	private static Tag007soundRecording01 uniqueInstance;

	private Tag007soundRecording01() {
		initialize();
		extractValidCodes();
	}

	public static Tag007soundRecording01 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007soundRecording01();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Specific material designation";
		id = "tag007soundRecording01";
		mqTag = "specificMaterialDesignation";
		positionStart = 1;
		positionEnd = 2;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
		codes = Utils.generateCodes(
			"d", "Sound disc",
			"e", "Cylinder",
			"g", "Sound cartridge",
			"i", "Sound-track film",
			"q", "Roll",
			"r", "Remote"
		);
	}
}