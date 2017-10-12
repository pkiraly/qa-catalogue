package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Modified record
 * https://www.loc.gov/marc/bibliographic/bd008a.html
 */
public class Tag008all38 extends ControlSubfield {
	private static Tag008all38 uniqueInstance;

	private Tag008all38() {
		initialize();
		extractValidCodes();
	}

	public static Tag008all38 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008all38();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Modified record";
		id = "tag008all38";
		mqTag = "modifiedRecord";
		positionStart = 38;
		positionEnd = 39;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008a.html";
		codes = Utils.generateCodes(
			" ", "Not modified",
			"d", "Dashed-on information omitted",
			"o", "Completely romanized/printed cards romanized",
			"r", "Completely romanized/printed cards in script",
			"s", "Shortened",
			"x", "Missing characters",
			"|", "No attempt to code"
		);
	}
}