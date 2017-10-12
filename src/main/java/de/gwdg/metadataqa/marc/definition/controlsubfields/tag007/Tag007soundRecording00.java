package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording00 extends ControlSubfield {
	private static Tag007soundRecording00 uniqueInstance;

	private Tag007soundRecording00() {
		initialize();
		extractValidCodes();
	}

	public static Tag007soundRecording00 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007soundRecording00();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Category of material";
		id = "tag007soundRecording00";
		mqTag = "categoryOfMaterial";
		positionStart = 0;
		positionEnd = 1;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
		codes = Utils.generateCodes(
			"s", "Sound recording"
		);
	}
}