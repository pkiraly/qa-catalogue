package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Kind of color stock or print
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture14 extends ControlSubfield {
	private static Tag007motionPicture14 uniqueInstance;

	private Tag007motionPicture14() {
		initialize();
		extractValidCodes();
	}

	public static Tag007motionPicture14 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007motionPicture14();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Kind of color stock or print";
		id = "tag007motionPicture14";
		mqTag = "kindOfColorStockOrPrint";
		positionStart = 14;
		positionEnd = 15;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
		codes = Utils.generateCodes(
			"a", "Imbibition dye transfer prints",
			"b", "Three-layer stock",
			"c", "Three layer stock, low fade",
			"d", "Duplitized stock",
			"n", "Not applicable",
			"u", "Unknown",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}