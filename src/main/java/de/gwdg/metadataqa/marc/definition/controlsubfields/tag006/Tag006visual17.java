package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Technique
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006visual17 extends ControlSubfieldDefinition {
	private static Tag006visual17 uniqueInstance;

	private Tag006visual17() {
		initialize();
		extractValidCodes();
	}

	public static Tag006visual17 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag006visual17();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Technique";
		id = "tag006visual17";
		mqTag = "technique";
		positionStart = 17;
		positionEnd = 18;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
		codes = Utils.generateCodes(
			"a", "Animation",
			"c", "Animation and live action",
			"l", "Live action",
			"n", "Not applicable",
			"u", "Unknown",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}