package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Speed
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording03 extends ControlSubfield {
	private static Tag007soundRecording03 uniqueInstance;

	private Tag007soundRecording03() {
		initialize();
		extractValidCodes();
	}

	public static Tag007soundRecording03 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007soundRecording03();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Speed";
		id = "tag007soundRecording03";
		mqTag = "speed";
		positionStart = 3;
		positionEnd = 4;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
		codes = Utils.generateCodes(
			"a", "16 rpm (discs)",
			"b", "33 1/3 rpm (discs)",
			"c", "45 rpm (discs)",
			"d", "78 rpm (discs)",
			"e", "8 rpm (discs)",
			"f", "1.4 m. per second (discs)",
			"h", "120 rpm (cylinders)",
			"i", "160 rpm (cylinders)",
			"k", "15/16 ips (tapes)",
			"l", "1 7/8 ips (tapes)",
			"m", "3 3/4 ips (tapes)",
			"n", "Not applicable",
			"o", "7 1/2 ips (tapes)",
			"p", "15 ips (tapes)",
			"r", "30 ips (tape)",
			"u", "Unknown",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}