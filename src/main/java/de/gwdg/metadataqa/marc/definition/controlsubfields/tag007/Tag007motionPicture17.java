package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Film inspection date
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture17 extends ControlSubfieldDefinition {
	private static Tag007motionPicture17 uniqueInstance;

	private Tag007motionPicture17() {
		initialize();
		extractValidCodes();
	}

	public static Tag007motionPicture17 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007motionPicture17();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Film inspection date";
		id = "tag007motionPicture17";
		mqTag = "filmInspectionDate";
		positionStart = 17;
		positionEnd = 23;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
	}
}