package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Reduction ration
 * https://www.loc.gov/marc/bibliographic/bd007h.html
 */
public class Tag007microform06 extends ControlSubfield {
	private static Tag007microform06 uniqueInstance;

	private Tag007microform06() {
		initialize();
		extractValidCodes();
	}

	public static Tag007microform06 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007microform06();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Reduction ration";
		id = "tag007microform06";
		mqTag = "reductionRation";
		positionStart = 6;
		positionEnd = 9;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007h.html";

		// TODO: pattern
	}
}