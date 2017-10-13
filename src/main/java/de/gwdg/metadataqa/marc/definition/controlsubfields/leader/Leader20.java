package de.gwdg.metadataqa.marc.definition.controlsubfields.leader;

import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Length of the length-of-field portion
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader20 extends ControlSubfield {
	private static Leader20 uniqueInstance;

	private Leader20() {
		initialize();
		extractValidCodes();
	}

	public static Leader20 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Leader20();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Length of the length-of-field portion";
		id = "leader20";
		mqTag = "lengthOfTheLengthOfFieldPortion";
		positionStart = 20;
		positionEnd = 21;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bdleader.html";
	}
}