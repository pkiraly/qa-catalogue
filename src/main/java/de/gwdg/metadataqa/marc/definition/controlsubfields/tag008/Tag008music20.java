package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Format of music
 * https://www.loc.gov/marc/bibliographic/bd008m.html
 */
public class Tag008music20 extends ControlSubfield {
	private static Tag008music20 uniqueInstance;

	private Tag008music20() {
		initialize();
		extractValidCodes();
	}

	public static Tag008music20 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag008music20();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Format of music";
		id = "tag008music20";
		mqTag = "formatOfMusic";
		positionStart = 20;
		positionEnd = 21;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008m.html";
		codes = Utils.generateCodes(
			"a", "Full score",
			"b", "Miniature or study score",
			"c", "Accompaniment reduced for keyboard",
			"d", "Voice score with accompaniment omitted",
			"e", "Condensed score or piano-conductor score",
			"g", "Close score",
			"h", "Chorus score",
			"i", "Condensed score",
			"j", "Performer-conductor part",
			"k", "Vocal score",
			"l", "Score",
			"m", "Multiple score formats",
			"n", "Not applicable",
			"p", "Piano score",
			"u", "Unknown",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}