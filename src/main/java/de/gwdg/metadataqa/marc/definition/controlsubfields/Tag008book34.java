package de.gwdg.metadataqa.marc.definition.controlsubfields;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

public class Tag008book34 extends ControlSubfield {
	private static Tag008book34 uniqueInstance;

	private Tag008book34() {
		initialize();
		extractValidCodes();
	}

	public static Tag008book34 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008book34();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Biography";
		id = "tag008book34";
		mqTag = "biography";
		positionStart = 34;
		positionEnd = 35;
		codes = Utils.generateCodes(
			" ", "No biographical material",
			"a", "Autobiography",
			"b", "Individual biography",
			"c", "Collective biography",
			"d", "Contains biographical information",
			"|", "No attempt to code"
		);
	}
}