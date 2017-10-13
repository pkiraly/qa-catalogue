package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Conference publication
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006continuing12 extends ControlSubfield {
	private static Tag006continuing12 uniqueInstance;

	private Tag006continuing12() {
		initialize();
		extractValidCodes();
	}

	public static Tag006continuing12 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag006continuing12();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Conference publication";
		id = "tag006continuing12";
		mqTag = "conferencePublication";
		positionStart = 12;
		positionEnd = 13;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
		codes = Utils.generateCodes(
			"0", "Not a conference publication",
			"1", "Conference publication",
			"|", "No attempt to code"
		);
	}
}