package de.gwdg.metadataqa.marc.definition.controlsubfields.leader;

import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Indicator count
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader10 extends ControlSubfieldDefinition {
	private static Leader10 uniqueInstance;

	private Leader10() {
		initialize();
		extractValidCodes();
	}

	public static Leader10 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Leader10();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Indicator count";
		id = "leader10";
		mqTag = "indicatorCount";
		positionStart = 10;
		positionEnd = 11;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bdleader.html";
	}
}