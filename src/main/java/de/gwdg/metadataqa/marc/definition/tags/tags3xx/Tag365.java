package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.PriceTypeCodeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Trade Price
 * http://www.loc.gov/marc/bibliographic/bd365.html
 */
public class Tag365 extends DataFieldDefinition {
	private static Tag365 uniqueInstance;

	private Tag365() {
		initialize();
		postCreation();
	}

	public static Tag365 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag365();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "365";
		label = "Trade Price";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd365.html";

		ind1 = new Indicator("Start/End designator")
			.setCodes(
				" ", "No information provided",
				"0", "Starting information",
				"1", "Ending information"
			);
		ind2 = new Indicator("State of issuance")
			.setCodes(
				" ", "Not specified",
				"0", "Closed",
				"1", "Open"
			);

		setSubfieldsWithCardinality(
			"a", "Price type code", "NR",
			"b", "Price amount", "NR",
			"c", "Currency code", "NR",
			"d", "Unit of pricing", "NR",
			"e", "Price note", "NR",
			"f", "Price effective from", "NR",
			"g", "Price effective until", "NR",
			"h", "Tax rate 1", "NR",
			"i", "Tax rate 2", "NR",
			"j", "ISO country code", "NR",
			"k", "MARC country code", "NR",
			"m", "Identification of pricing entity", "NR",
			"2", "Source of price type code", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("d").setCodes(
			"00", "Per copy of whole product [default value]",
			"01", "Per page for printed loose-leaf content only"
		);
		getSubfield("k").setCodeList(CountryCodes.getInstance());
		getSubfield("2").setCodeList(PriceTypeCodeSourceCodes.getInstance());

		getSubfield("6").setContentParser(LinkageParser.getInstance());
	}
}
