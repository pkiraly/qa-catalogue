package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007h.html
 */
public class Tag007microform00 extends ControlSubfieldDefinition {
	private static Tag007microform00 uniqueInstance;

	private Tag007microform00() {
		initialize();
		extractValidCodes();
	}

	public static Tag007microform00 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007microform00();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Category of material";
		id = "tag007microform00";
		mqTag = "categoryOfMaterial";
		positionStart = 0;
		positionEnd = 1;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007h.html";
		codes = Utils.generateCodes(
			"h", "Microform"
		);
	}
}