package de.gwdg.metadataqa.marc.definition.controlsubfields.leader;

import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Length of the starting-character-position portion
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader21 extends ControlSubfieldDefinition {
	private static Leader21 uniqueInstance;

	private Leader21() {
		initialize();
		extractValidCodes();
	}

	public static Leader21 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Leader21();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Length of the starting-character-position portion";
		id = "leader21";
		mqTag = "lengthOfTheStartingCharacterPositionPortion";
		positionStart = 21;
		positionEnd = 22;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bdleader.html";
	}
}