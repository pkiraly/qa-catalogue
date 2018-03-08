package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Configuration of playback channels
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture08 extends ControlSubfieldDefinition {
	private static Tag007motionPicture08 uniqueInstance;

	private Tag007motionPicture08() {
		initialize();
		extractValidCodes();
	}

	public static Tag007motionPicture08 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007motionPicture08();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Configuration of playback channels";
		id = "tag007motionPicture08";
		mqTag = "configurationOfPlaybackChannels";
		positionStart = 8;
		positionEnd = 9;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
		codes = Utils.generateCodes(
			"k", "Mixed",
			"m", "Monaural",
			"n", "Not applicable",
			"q", "Quadraphonic, multichannel, or surround",
			"s", "Stereophonic",
			"u", "Unknown",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}