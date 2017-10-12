package de.gwdg.metadataqa.marc.definition.controlsubfields;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

public class Tag008book31 extends ControlSubfield {
	private static Tag008book31 uniqueInstance;

	private Tag008book31() {
		initialize();
		extractValidCodes();
	}

	public static Tag008book31 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008book31();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Index";
		id = "tag008book31";
		mqTag = "index";
		positionStart = 31;
		positionEnd = 32;
		codes = Utils.generateCodes(
			"0", "No index",
			"1", "Index present",
			"|", "No attempt to code"
		);
	}
}