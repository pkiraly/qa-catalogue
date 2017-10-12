package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Conference publication
 * https://www.loc.gov/marc/bibliographic/bd008s.html
 */
public class Tag008continuing29 extends ControlSubfield {
	private static Tag008continuing29 uniqueInstance;

	private Tag008continuing29() {
		initialize();
		extractValidCodes();
	}

	public static Tag008continuing29 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008continuing29();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Conference publication";
		id = "tag008continuing29";
		mqTag = "conferencePublication";
		positionStart = 29;
		positionEnd = 30;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008s.html";
		codes = Utils.generateCodes(
			"0", "Not a conference publication",
			"1", "Conference publication",
			"|", "No attempt to code"
		);
	}
}