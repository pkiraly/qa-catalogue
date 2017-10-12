package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Literary form
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag008book33 extends ControlSubfield {
	private static Tag008book33 uniqueInstance;

	private Tag008book33() {
		initialize();
		extractValidCodes();
	}

	public static Tag008book33 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008book33();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Literary form";
		id = "tag008book33";
		mqTag = "literaryForm";
		positionStart = 33;
		positionEnd = 34;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008b.html";
		codes = Utils.generateCodes(
			"0", "Not fiction (not further specified)",
			"1", "Fiction (not further specified)",
			"d", "Dramas",
			"e", "Essays",
			"f", "Novels",
			"h", "Humor, satires, etc.",
			"i", "Letters",
			"j", "Short stories",
			"m", "Mixed forms",
			"p", "Poetry",
			"s", "Speeches",
			"u", "Unknown",
			"|", "No attempt to code"
		);
	}
}