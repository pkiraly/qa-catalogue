package de.gwdg.metadataqa.marc.definition.controlsubfields;

import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Language
 * https://www.loc.gov/marc/bibliographic/bd008a.html
 */
public class Tag008all35 extends ControlSubfield {
	private static Tag008all35 uniqueInstance;

	private Tag008all35() {
		initialize();
		extractValidCodes();
	}

	public static Tag008all35 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008all35();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Language";
		id = "tag008all35";
		mqTag = "language";
		positionStart = 35;
		positionEnd = 38;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008a.html";

		// TODO: pattern?
	}
}