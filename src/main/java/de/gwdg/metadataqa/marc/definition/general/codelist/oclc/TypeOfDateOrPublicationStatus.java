package de.gwdg.metadataqa.marc.definition.general.codelist.oclc;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.general.codelist.CodeList;

/**
 * DtSt Type of Date/Publication Status
 * http://www.oclc.org/bibformats/en/fixedfield/dtst.html
 */
public class TypeOfDateOrPublicationStatus extends CodeList {

	private void initialize() {
		codes = Utils.generateCodes(
			"b", "B.C. date",
			"e", "Detailed date",
			"i", "Inclusive date",
			"k", "Range of years",
			"m", "Initial/terminal date",
			"n", "Unknown date",
			"p", "Distribution/production date",
			"q", "Questionable date",
			"r", "Reprint/original date",
			"s", "Single date",
			"t", "Publication and copyright date"
		);
		indexCodes();
	}

	private static TypeOfDateOrPublicationStatus uniqueInstance;

	private TypeOfDateOrPublicationStatus() {
		initialize();
	}

	public static TypeOfDateOrPublicationStatus getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new TypeOfDateOrPublicationStatus();
		return uniqueInstance;
	}
}