package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Holding Scheme Source Codes
 * http://www.loc.gov/standards/sourcelist/holding-scheme.html
 * Holdings and Bibliographic records
 * 866 $2 (Textual Holdings - Basic Bibliographic Unit / Source of notation)
 * 867 $2 (Textual Holdings - Supplementary Material / Source of notation)
 * 868 $2 (Textual Holdings - Indexes / Source of notation)
 */
public class HoldingSchemeSourceCodes extends CodeList {

	private void initialize() {
		name = "Holding Scheme Source Codes";
		url = "http://www.loc.gov/standards/sourcelist/holding-scheme.html";
		codes = Utils.generateCodes(
			"iso10324", "Holdings Statements-Summary Level (ISO 10324) [holdings statements for Bbbliographic items (ANSI/NISO Z39.71)]",
			"z39-42", "Serial Holdings Statements at the Summary Level (ANSI Z39.42) [replaced by Z39.44]",
			"z39-71", "Holdings Statements for Bibliographic Items (ANSI/NISO Z39.71) [formerly Serial Holdings Statement (ANSI Z39.44) and Holdings Statements for Non-Serial Items(ANSI/NISO Z39.57)]"
		);
		indexCodes();
	}

	private static HoldingSchemeSourceCodes uniqueInstance;

	private HoldingSchemeSourceCodes() {
		initialize();
	}

	public static HoldingSchemeSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new HoldingSchemeSourceCodes();
		return uniqueInstance;
	}
}