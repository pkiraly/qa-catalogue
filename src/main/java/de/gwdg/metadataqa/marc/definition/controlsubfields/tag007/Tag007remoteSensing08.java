package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Sensor type
 * https://www.loc.gov/marc/bibliographic/bd007r.html
 */
public class Tag007remoteSensing08 extends ControlSubfieldDefinition {
	private static Tag007remoteSensing08 uniqueInstance;

	private Tag007remoteSensing08() {
		initialize();
		extractValidCodes();
	}

	public static Tag007remoteSensing08 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007remoteSensing08();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Sensor type";
		id = "tag007remoteSensing08";
		mqTag = "sensorType";
		positionStart = 8;
		positionEnd = 9;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007r.html";
		codes = Utils.generateCodes(
			"a", "Active",
			"b", "Passive",
			"u", "Unknown",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}