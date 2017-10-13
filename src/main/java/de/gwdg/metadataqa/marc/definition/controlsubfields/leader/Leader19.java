package de.gwdg.metadataqa.marc.definition.controlsubfields.leader;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Multipart resource record level
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader19 extends ControlSubfield {
	private static Leader19 uniqueInstance;

	private Leader19() {
		initialize();
		extractValidCodes();
	}

	public static Leader19 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Leader19();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Multipart resource record level";
		id = "leader19";
		mqTag = "multipartResourceRecordLevel";
		positionStart = 19;
		positionEnd = 20;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bdleader.html";
		codes = Utils.generateCodes(
			" ", "Not specified or not applicable",
			"a", "Set",
			"b", "Part with independent title",
			"c", "Part with dependent title"
		);
	}
}