package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Government publication
 * https://www.loc.gov/marc/bibliographic/bd008p.html
 */
public class Tag008map28 extends ControlSubfieldDefinition {
	private static Tag008map28 uniqueInstance;

	private Tag008map28() {
		initialize();
		extractValidCodes();
	}

	public static Tag008map28 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008map28();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Government publication";
		id = "tag008map28";
		mqTag = "governmentPublication";
		positionStart = 28;
		positionEnd = 29;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008p.html";
		codes = Utils.generateCodes(
			" ", "Not a government publication",
			"a", "Autonomous or semi-autonomous component",
			"c", "Multilocal",
			"f", "Federal/national",
			"i", "International intergovernmental",
			"l", "Local",
			"m", "Multistate",
			"o", "Government publication-level undetermined",
			"s", "State, provincial, territorial, dependent, etc.",
			"u", "Unknown if item is government publication",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}