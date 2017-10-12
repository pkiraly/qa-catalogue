package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Regularity
 * https://www.loc.gov/marc/bibliographic/bd008s.html
 */
public class Tag008continuing19 extends ControlSubfield {
	private static Tag008continuing19 uniqueInstance;

	private Tag008continuing19() {
		initialize();
		extractValidCodes();
	}

	public static Tag008continuing19 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008continuing19();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Regularity";
		id = "tag008continuing19";
		mqTag = "regularity";
		positionStart = 19;
		positionEnd = 20;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008s.html";
		codes = Utils.generateCodes(
			"n", "Normalized irregular",
			"r", "Regular",
			"u", "Unknown",
			"x", "Completely irregular",
			"|", "No attempt to code"
		);
	}
}