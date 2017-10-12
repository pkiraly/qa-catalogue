package de.gwdg.metadataqa.marc.definition.controlsubfields;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Festschrift
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag008book30 extends ControlSubfield {
	private static Tag008book30 uniqueInstance;

	private Tag008book30() {
		initialize();
		extractValidCodes();
	}

	public static Tag008book30 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008book30();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Festschrift";
		id = "tag008book30";
		mqTag = "festschrift";
		positionStart = 30;
		positionEnd = 31;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008b.html";
		codes = Utils.generateCodes(
			"0", "Not a festschrift",
			"1", "Festschrift",
			"|", "No attempt to code"
		);
	}
}