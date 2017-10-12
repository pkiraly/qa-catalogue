package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Sound
 * https://www.loc.gov/marc/bibliographic/bd007c.html
 */
public class Tag007electro05 extends ControlSubfield {
	private static Tag007electro05 uniqueInstance;

	private Tag007electro05() {
		initialize();
		extractValidCodes();
	}

	public static Tag007electro05 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007electro05();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Sound";
		id = "tag007electro05";
		mqTag = "sound";
		positionStart = 5;
		positionEnd = 6;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007c.html";
		codes = Utils.generateCodes(
			" ", "No sound (silent)",
			"a", "Sound",
			"u", "Unknown",
			"|", "No attempt to code"
		);
	}
}