package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Medium for sound
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture06 extends ControlSubfield {
	private static Tag007motionPicture06 uniqueInstance;

	private Tag007motionPicture06() {
		initialize();
		extractValidCodes();
	}

	public static Tag007motionPicture06 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007motionPicture06();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Medium for sound";
		id = "tag007motionPicture06";
		mqTag = "mediumForSound";
		positionStart = 6;
		positionEnd = 7;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
		codes = Utils.generateCodes(
			" ", "No sound (silent)",
			"a", "Optical sound track on motion picture film",
			"b", "Magnetic sound track on motion picture film",
			"c", "Magnetic audio tape in cartridge",
			"d", "Sound disc",
			"e", "Magnetic audio tape on reel",
			"f", "Magnetic audio tape in cassette",
			"g", "Optical and magnetic sound track on motion picture film",
			"h", "Videotape",
			"i", "Videodisc",
			"u", "Unknown",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}