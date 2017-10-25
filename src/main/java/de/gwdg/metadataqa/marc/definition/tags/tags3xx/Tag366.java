package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.AvailabilityStatusCodeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;

/**
 * Trade Availability Information
 * http://www.loc.gov/marc/bibliographic/bd366.html
 */
public class Tag366 extends DataFieldDefinition {
	private static Tag366 uniqueInstance;

	private Tag366() {
		initialize();
		postCreation();
	}

	public static Tag366 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag366();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "366";
		label = "Trade Availability Information";
		mqTag = "TradeAvailabilityInformation";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Publishers' compressed title identification", "NR",
			"b", "Detailed date of publication", "NR",
			"c", "Availability status code", "NR",
			"d", "Expected next availability date", "NR",
			"e", "Note", "NR",
			"f", "Publisher's discount category", "NR",
			"g", "Date made out of print", "NR",
			"j", "ISO country code", "NR",
			"k", "MARC country code", "NR",
			"m", "Identification of agency", "NR",
			"2", "Source of availability status code", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		// TODO $b - regex: yyyymmdd
		// TODO $c - regex: yyyymmdd
		// TODO $d - regex: yyyymmdd
		// TODO $g - regex: yyyymmdd

		getSubfield("k").setCodeList(CountryCodes.getInstance());
		getSubfield("2").setCodeList(AvailabilityStatusCodeSourceCodes.getInstance());
	}
}
