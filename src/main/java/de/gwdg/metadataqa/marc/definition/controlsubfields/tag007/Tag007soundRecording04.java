package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Configuration of playback channels
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording04 extends ControlSubfield {
	private static Tag007soundRecording04 uniqueInstance;

	private Tag007soundRecording04() {
		initialize();
		extractValidCodes();
	}

	public static Tag007soundRecording04 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007soundRecording04();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Configuration of playback channels";
		id = "tag007soundRecording04";
		mqTag = "configurationOfPlaybackChannels";
		positionStart = 4;
		positionEnd = 5;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
		codes = Utils.generateCodes(
			"m", "Monaural",
			"q", "Quadraphonic, multichannel, or surround",
			"s", "Stereophonic",
			"u", "Unknown",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}