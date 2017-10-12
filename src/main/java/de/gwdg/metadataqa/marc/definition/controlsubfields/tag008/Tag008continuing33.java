package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Original alphabet or script of title
 * https://www.loc.gov/marc/bibliographic/bd008s.html
 */
public class Tag008continuing33 extends ControlSubfield {
	private static Tag008continuing33 uniqueInstance;

	private Tag008continuing33() {
		initialize();
		extractValidCodes();
	}

	public static Tag008continuing33 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008continuing33();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Original alphabet or script of title";
		id = "tag008continuing33";
		mqTag = "originalAlphabetOrScriptOfTitle";
		positionStart = 33;
		positionEnd = 34;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008s.html";
		codes = Utils.generateCodes(
			" ", "No alphabet or script given/No key title",
			"a", "Basic Roman",
			"b", "Extended Roman",
			"c", "Cyrillic",
			"d", "Japanese",
			"e", "Chinese",
			"f", "Arabic",
			"g", "Greek",
			"h", "Hebrew",
			"i", "Thai",
			"j", "Devanagari",
			"k", "Korean",
			"l", "Tamil",
			"u", "Unknown",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}