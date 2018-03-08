package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Specific material designation
 * https://www.loc.gov/marc/bibliographic/bd007z.html
 */
public class Tag007unspecified01 extends ControlSubfieldDefinition {
	private static Tag007unspecified01 uniqueInstance;

	private Tag007unspecified01() {
		initialize();
		extractValidCodes();
	}

	public static Tag007unspecified01 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007unspecified01();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Specific material designation";
		id = "tag007unspecified01";
		mqTag = "specificMaterialDesignation";
		positionStart = 1;
		positionEnd = 2;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007z.html";
		codes = Utils.generateCodes(
			"m", "Multiple physical forms",
			"u", "Unspecified",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}