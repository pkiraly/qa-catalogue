package de.gwdg.metadataqa.marc.definition.general.codelist.oclc;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.general.codelist.CodeList;

/**
 * Regl: Regularity
 * http://www.oclc.org/bibformats/en/fixedfield/regl.html
 */
public class Regularity extends CodeList {

	static {
		codes = Utils.generateCodes(
			"n", "Normalized irregular",
			"r", "Regular",
			"u", "Unknown",
			"x", "Completely irregular",
			" ", "No attempt to code"
		);
		indexCodes();
	}

	private static Regularity uniqueInstance;

	private Regularity() {}

	public static Regularity getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Regularity();
		return uniqueInstance;
	}
}