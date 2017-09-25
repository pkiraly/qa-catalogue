package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Geographic Area Code and Term Source Codes
 * http://www.loc.gov/standards/sourcelist/geographic-area.html
 */
public class GeographicAreaSourceCodes extends CodeList {

	private void initialize() {
		codes = Utils.generateCodes(
				"ccga", "Cadre de classement geographique actuel (Paris: Bibliothèque Nationale)",
				"marcgac", "MARC Code List for Geographic Areas"
		);
		indexCodes();
	}

	private static GeographicAreaSourceCodes uniqueInstance;

	private GeographicAreaSourceCodes() {
		initialize();
	}

	public static GeographicAreaSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new GeographicAreaSourceCodes();
		return uniqueInstance;
	}
}
