package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Production/reproduction details
 * https://www.loc.gov/marc/bibliographic/bd007a.html
 */
public class Tag007map06 extends ControlSubfield {
	private static Tag007map06 uniqueInstance;

	private Tag007map06() {
		initialize();
		extractValidCodes();
	}

	public static Tag007map06 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007map06();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Production/reproduction details";
		id = "tag007map06";
		mqTag = "productionOrReproductionDetails";
		positionStart = 6;
		positionEnd = 7;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007a.html";
		codes = Utils.generateCodes(
			"a", "Photocopy, blueline print",
			"b", "Photocopy",
			"c", "Pre-production",
			"d", "Film",
			"u", "Unknown",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}