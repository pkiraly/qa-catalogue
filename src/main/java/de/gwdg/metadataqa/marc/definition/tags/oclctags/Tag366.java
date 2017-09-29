package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Trade Availability Information
 * http://www.oclc.org/bibformats/en/3xx/366.html
 */
public class Tag366 extends DataFieldDefinition {

	private static Tag366 uniqueInstance;

	private Tag366() {
		initialize();
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
		cardinality = Cardinality.Nonrepeatable;

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
			"m", "Identification of pricing entity", "NR",
			"2", "Source of availability status code", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setMqTag("compressedTitle");
		getSubfield("b").setMqTag("date");
		getSubfield("c").setMqTag("availability");
		getSubfield("d").setMqTag("expectedNextAvailability");
		getSubfield("e").setMqTag("note");
		getSubfield("f").setMqTag("discountCategory");
		getSubfield("g").setMqTag("dateMadeOutOfPrint");
		getSubfield("j").setMqTag("isoCountryCode");
		getSubfield("k").setMqTag("marcCountryCode");
		getSubfield("m").setMqTag("pricing");
		getSubfield("2").setMqTag("source");
		getSubfield("8").setMqTag("fieldLink");
	}
}
