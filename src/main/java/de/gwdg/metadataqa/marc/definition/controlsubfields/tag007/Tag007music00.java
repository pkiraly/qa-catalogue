package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007q.html
 */
public class Tag007music00 extends ControlSubfield {
	private static Tag007music00 uniqueInstance;

	private Tag007music00() {
		initialize();
		extractValidCodes();
	}

	public static Tag007music00 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007music00();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Category of material";
		id = "tag007music00";
		mqTag = "categoryOfMaterial";
		positionStart = 0;
		positionEnd = 1;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007q.html";
		codes = Utils.generateCodes(
			"q", "Notated music"
		);
	}
}