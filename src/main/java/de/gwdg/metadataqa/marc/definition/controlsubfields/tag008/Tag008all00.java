package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Date entered on file
 * https://www.loc.gov/marc/bibliographic/bd008a.html
 */
public class Tag008all00 extends ControlSubfield {
	private static Tag008all00 uniqueInstance;

	private Tag008all00() {
		initialize();
		extractValidCodes();
	}

	public static Tag008all00 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008all00();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Date entered on file";
		id = "tag008all00";
		mqTag = "dateEnteredOnFile";
		positionStart = 0;
		positionEnd = 6;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008a.html";

		// TODO: pattern: yymmdd
	}
}