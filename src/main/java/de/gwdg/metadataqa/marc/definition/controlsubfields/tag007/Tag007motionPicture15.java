package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Deterioration stage
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture15 extends ControlSubfield {
	private static Tag007motionPicture15 uniqueInstance;

	private Tag007motionPicture15() {
		initialize();
		extractValidCodes();
	}

	public static Tag007motionPicture15 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007motionPicture15();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Deterioration stage";
		id = "tag007motionPicture15";
		mqTag = "deteriorationStage";
		positionStart = 15;
		positionEnd = 16;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
		codes = Utils.generateCodes(
			"a", "None apparent",
			"b", "Nitrate: suspicious odor",
			"c", "Nitrate: pungent odor",
			"d", "Nitrate: brownish, discoloration, fading, dusty",
			"e", "Nitrate: sticky",
			"f", "Nitrate: frothy, bubbles, blisters",
			"g", "Nitrate: congealed",
			"h", "Nitrate: powder",
			"k", "Non-nitrate: detectable deterioration",
			"l", "Non-nitrate: advanced deterioration",
			"m", "Non-nitrate: disaster",
			"|", "No attempt to code"
		);
	}
}