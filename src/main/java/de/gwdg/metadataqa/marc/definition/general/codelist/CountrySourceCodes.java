package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Country Code and Term Source Codes
 * http://www.loc.gov/standards/sourcelist/country.html
 */
public class CountrySourceCodes extends CodeList {

	private void initialize() {
		codes = Utils.generateCodes(
			"marccountry", "MARC Code List for Countries (Washington, DC: Library of Congress)",
			"iso3166", "Codes for the representation of names of countries and their subdivisions-Part 1, Country codes (Geneva: International Organization for Standardization)",
			"iso3166-2", "Codes for the representation of names of countries and their subdivisions-Part 2, Country subdivision codes (Geneva: International Organization for Standardization)",
			"swdl", "Ländercode der Schlagwortnormdatei (SWD) (Leipzig, Frankfurt am Main, Berlin: Deutsche Nationalbibliothek)"
		);
		indexCodes();
	}

	private static CountrySourceCodes uniqueInstance;

	private CountrySourceCodes() {
		initialize();
	}

	public static CountrySourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new CountrySourceCodes();
		return uniqueInstance;
	}
}