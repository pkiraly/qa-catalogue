package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Sound on medium or separate
 * https://www.loc.gov/marc/bibliographic/bd007g.html
 */
public class Tag007projected05 extends ControlSubfieldDefinition {
	private static Tag007projected05 uniqueInstance;

	private Tag007projected05() {
		initialize();
		extractValidCodes();
	}

	public static Tag007projected05 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007projected05();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Sound on medium or separate";
		id = "tag007projected05";
		mqTag = "soundOnMediumOrSeparate";
		positionStart = 5;
		positionEnd = 6;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007g.html";
		codes = Utils.generateCodes(
			" ", "No sound (silent)",
			"a", "Sound on medium",
			"b", "Sound separate from medium",
			"u", "Unknown",
			"|", "No attempt to code"
		);
	}
}