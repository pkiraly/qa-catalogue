package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Place of publication, production, or execution
 * https://www.loc.gov/marc/bibliographic/bd008a.html
 */
public class Tag008all15 extends ControlSubfield {
	private static Tag008all15 uniqueInstance;

	private Tag008all15() {
		initialize();
		extractValidCodes();
	}

	public static Tag008all15 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008all15();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Place of publication, production, or execution";
		id = "tag008all15";
		mqTag = "placeOfPublicationProductionOrExecution";
		positionStart = 15;
		positionEnd = 18;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008a.html";

		// TODO: pattern?
	}
}