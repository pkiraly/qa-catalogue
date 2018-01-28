package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Language Code and Term Source Codes
 * http://www.loc.gov/standards/sourcelist/language.html
 */
public class LanguageCodeAndTermSourceCodes extends CodeList {

	private void initialize() {
		name = "Language Code and Term Source Codes";
		url = "http://www.loc.gov/standards/sourcelist/language.html";
		codes = Utils.generateCodes(
			"din2335", "Sprachenzeichen: DIN 2335 (Berlin: Beuth)",
			"glotto", "Glottolog",
			"iso639-1", "Codes for the representation of names of languages--Part 1: Alpha-2 code (ISO 639-1:2002) (Geneva: International Organization for Standardization)",
			"iso639-2b", "Codes for the representation of names of languages--Part 2: Alpha-3 code (ISO 639-2B:2002) (Geneva: International Organization for Standardization). The bibliographic language codes are identical to both NISO Z39.53 and the MARC Code List for Languages.",
			"iso639-3", "Codes for the representation of names of languages--Part 3: Alpha-3 code for comprehensive coverage of languages (Geneva: International Organization for Standardization)",
			"knia", "Kody naimenovanii íàzykov: GOST 7.75-97 (Minsk: Mezhgosudarstvennyi sovet po standartizatsii, metrologii i sertifikatsii)",
			"rfc3066", "Tags for the Identification of Languages (January 2001) (The Internet Society). Replaced by RFC 4646 and RFC 4647",
			"rfc4646", "Tags for Identifying Languages (September 2006) (The Internet Society). In combination with RFC 4647, replaces RFC 3066.  A language identifier as specified by the Internet Best Current Practice specification RFC4646. This document gives guidance on the use of ISO 639-1, ISO 639-2, and ISO 639-3 language identifiers with optional secondary subtags and extensions. Replaced by RFC 5646.",
			"rfc5646", "Tags for Identifying Languages (September 2009) (The Internet Society). In combination with RFC 5645, replaces RFC 4646.  A language identifier as specified by the Internet Best Current Practice specification RFC5646. This document gives guidance on the use of ISO 639-1, ISO 639-2, ISO 639-3, and ISO-639-5 language identifiers."
		);
		indexCodes();
	}

	private static LanguageCodeAndTermSourceCodes uniqueInstance;

	private LanguageCodeAndTermSourceCodes() {
		initialize();
	}

	public static LanguageCodeAndTermSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new LanguageCodeAndTermSourceCodes();
		return uniqueInstance;
	}
}