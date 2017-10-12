package de.gwdg.metadataqa.marc.definition.controlsubfields;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Government publication
 * https://www.loc.gov/marc/bibliographic/bd008s.html
 */
public class Tag008continuing28 extends ControlSubfield {
	private static Tag008continuing28 uniqueInstance;

	private Tag008continuing28() {
		initialize();
		extractValidCodes();
	}

	public static Tag008continuing28 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008continuing28();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Government publication";
		id = "tag008continuing28";
		mqTag = "governmentPublication";
		positionStart = 28;
		positionEnd = 29;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008s.html";
		codes = Utils.generateCodes(
			" ", "Not a government publication",
			"a", "Autonomous or semi-autonomous component",
			"c", "Multilocal",
			"f", "Federal/national",
			"i", "International intergovernmental",
			"l", "Local",
			"m", "Multistate",
			"o", "Government publication-level undetermined",
			"s", "State, provincial, territorial, dependent, etc. ",
			"u", "Unknown if item is government publication",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}