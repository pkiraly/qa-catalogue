package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Date and Time Scheme Source Codes
 * http://www.loc.gov/standards/sourcelist/date-time.html
 */
public class DateSourceCodes extends CodeList {

	private void initialize() {
		codes = Utils.generateCodes(
			"edtf", "Extended Date/Time Format",
			"iso8601", "Code identifies formatted dates allowed in ISO 8601 which use the alternative described as \"basic\" (i.e., with minimum number of separators) rather than \"extended\" (i.e., with separators). This alternative specified in the standard uses the following date patterns: YYYY; YYYY-MM if only year and month given; YYYYMMDD if year, month, and day are included (hours, minutes, seconds may also be added: Thhmmss.s). It is also used for other encodings specified in ISO 8601, e.g., date ranges, which are in the form of <date/time>/<date/time>.",
			"marc", "Code identifies dates formatted according to MARC 21 rules in field 008/07-14 for dates of publication/issuance. Examples include: YYYY (for year), MMDD (for month and day), 19uu (MARC convention showing unknown digits in a year date), 9999 (MARC convention showing that the end year date has not occurred or is not known). See Legal Characters section under field 008/06 of MARC Bibliographic.",
			"temper", "Temporal Enumerated Ranges (August 2007)",
			"w3cdtf", "Code identifies dates following the W3C profile of ISO 8601, Date and Time Formats, that specifies the pattern: YYYY-MM-DD. If hours, minutes, and seconds are also needed the following pattern is used: YYYY-MM-DDThh:mm:ss."
		);
		indexCodes();
	}

	private static DateSourceCodes uniqueInstance;

	private DateSourceCodes() {
		initialize();
	}

	public static DateSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new DateSourceCodes();
		return uniqueInstance;
	}
}