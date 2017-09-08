package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

public class MusicalIncipitSchemeSourceCodes extends CodeList {
	static {
		codes = Utils.generateCodes(
				"da", "DARMS [Digital Alternate Representation of Musical Scores] code (Alexander Brinkman. Pascal Programming for Music Research. Chicago: University of Chicago Press, 1990)",
				"pe", "Plaine & Easie Code (International Association of Music Libraries)"
		);
		indexCodes();
	}

	private static MusicalIncipitSchemeSourceCodes uniqueInstance;

	private MusicalIncipitSchemeSourceCodes() {
	}

	public static MusicalIncipitSchemeSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new MusicalIncipitSchemeSourceCodes();
		return uniqueInstance;
	}
}
