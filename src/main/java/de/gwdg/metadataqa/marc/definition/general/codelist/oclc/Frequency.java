package de.gwdg.metadataqa.marc.definition.general.codelist.oclc;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.general.codelist.CodeList;

/**
 * Freq: Frequency
 * http://www.oclc.org/bibformats/en/fixedfield/freq.html
 */
public class Frequency extends CodeList {

	static {
		codes = Utils.generateCodes(
			" ", "No determinable frequency. The item has no determinable frequency. Use when the frequency is known to be intentionally irregular.",
			"a", "Annual. The item is issued or updated once a year.",
			"b", "Bimonthly. The item is issued or updated every two months. Use also for publications whose frequency is identified as six, seven or eight numbers a year.",
			"c", "Semiweekly. The item is issued or updated twice a week.",
			"d", "Daily. The item is issued or updated once a day. May include Saturday and Sunday.",
			"e", "Biweekly. The item is issued or updated every two weeks.",
			"f", "Semiannual. The item is issued or updated twice a year. Use also for publications whose frequency is identified as two numbers a year.",
			"g", "Biennial. The item is issued or updated every two years.",
			"h", "Triennial. The item is issued or updated every three years.",
			"i", "Three times a week. The item is issued or updated three times a week.",
			"j", "Three times a month. The item is issued or updated three times a month.",
			"k", "Continuously updated. The item is updated more frequently than daily.",
			"m", "Monthly. The item is issued or updated every month. Includes frequencies of nine, ten, eleven or twelve numbers a year.",
			"q", "Quarterly. The item is issued or updated every three months. Use also for publications whose frequency is identified as four numbers a year.",
			"s", "Semimonthly. The item is issued or updated twice a month.",
			"t", "Three times a year. The item is issued or updated three times a year.",
			"u", "Unknown. The current frequency is not known. When code u is used in Freq, code u must also be used in Regl.",
			"w", "Weekly. The item is issued once a week.",
			"z", "Other. The frequency of the item cannot be defined by any of the other codes."
		);
		indexCodes();
	}

	private static Frequency uniqueInstance;

	private Frequency() {}

	public static Frequency getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Frequency();
		return uniqueInstance;
	}
}