package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Biography
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006book17 extends ControlSubfield {
	private static Tag006book17 uniqueInstance;

	private Tag006book17() {
		initialize();
		extractValidCodes();
	}

	public static Tag006book17 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag006book17();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Biography";
		id = "tag006book17";
		mqTag = "biography";
		positionStart = 17;
		positionEnd = 18;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
		codes = Utils.generateCodes(
			" ", "No biographical material",
			"a", "Autobiography",
			"b", "Individual biography",
			"c", "Collective biography",
			"d", "Contains biographical information",
			"|", "No attempt to code"
		);
	}
}