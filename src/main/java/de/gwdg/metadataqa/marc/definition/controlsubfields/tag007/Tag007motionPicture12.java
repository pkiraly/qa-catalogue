package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Base of film
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture12 extends ControlSubfieldDefinition {
	private static Tag007motionPicture12 uniqueInstance;

	private Tag007motionPicture12() {
		initialize();
		extractValidCodes();
	}

	public static Tag007motionPicture12 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007motionPicture12();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Base of film";
		id = "tag007motionPicture12";
		mqTag = "baseOfFilm";
		positionStart = 12;
		positionEnd = 13;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
		codes = Utils.generateCodes(
			"a", "Safety base, undetermined",
			"c", "Safety base, acetate undetermined",
			"d", "Safety base, diacetate",
			"i", "Nitrate base",
			"m", "Mixed base (nitrate and safety)",
			"n", "Not applicable",
			"p", "Safety base, polyester",
			"r", "Safety base, mixed",
			"t", "Safety base, triacetate",
			"u", "Unknown",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}