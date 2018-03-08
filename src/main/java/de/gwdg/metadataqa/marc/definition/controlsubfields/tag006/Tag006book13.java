package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Festschrift
 * same as 008/30
 * https://www.loc.gov/marc/bibliographic/bd006.html
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag006book13 extends ControlSubfieldDefinition {
	private static Tag006book13 uniqueInstance;

	private Tag006book13() {
		initialize();
		extractValidCodes();
	}

	public static Tag006book13 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag006book13();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Festschrift";
		id = "tag006book13";
		mqTag = "festschrift";
		positionStart = 13;
		positionEnd = 14;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
		codes = Utils.generateCodes(
			"0", "Not a festschrift",
			"1", "Festschrift",
			"|", "No attempt to code"
		);
	}
}