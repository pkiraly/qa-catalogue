package de.gwdg.metadataqa.marc.definition.controlsubfields;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Cataloging source
 * https://www.loc.gov/marc/bibliographic/bd008a.html
 */
public class Tag008all39 extends ControlSubfield {
	private static Tag008all39 uniqueInstance;

	private Tag008all39() {
		initialize();
		extractValidCodes();
	}

	public static Tag008all39 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008all39();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Cataloging source";
		id = "tag008all39";
		mqTag = "catalogingSource";
		positionStart = 39;
		positionEnd = 40;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008a.html";
		codes = Utils.generateCodes(
			" ", "National bibliographic agency",
			"c", "Cooperative cataloging program",
			"d", "Other",
			"u", "Unknown",
			"|", "No attempt to code"
		);
	}
}