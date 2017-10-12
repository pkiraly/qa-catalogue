package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Specific material designation
 * https://www.loc.gov/marc/bibliographic/bd007t.html
 */
public class Tag007text01 extends ControlSubfield {
	private static Tag007text01 uniqueInstance;

	private Tag007text01() {
		initialize();
		extractValidCodes();
	}

	public static Tag007text01 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007text01();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Specific material designation";
		id = "tag007text01";
		mqTag = "specificMaterialDesignation";
		positionStart = 1;
		positionEnd = 2;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007t.html";
		codes = Utils.generateCodes(
			"a", "Regular print",
			"b", "Large print",
			"c", "Braille",
			"d", "Loose-leaf",
			"u", "Unspecified",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}