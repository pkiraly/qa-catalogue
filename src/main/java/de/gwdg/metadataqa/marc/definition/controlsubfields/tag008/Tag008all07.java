package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Date 1
 * https://www.loc.gov/marc/bibliographic/bd008a.html
 */
public class Tag008all07 extends ControlSubfield {
	private static Tag008all07 uniqueInstance;

	private Tag008all07() {
		initialize();
		extractValidCodes();
	}

	public static Tag008all07 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008all07();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Date 1";
		id = "tag008all07";
		mqTag = "date1";
		positionStart = 7;
		positionEnd = 11;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008a.html";

		// TODO: pattern?

	}
}