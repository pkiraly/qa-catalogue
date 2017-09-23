package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Patent Control Information
 * http://www.loc.gov/marc/bibliographic/bd013.html
 */
public class Tag013 extends DataFieldDefinition {

	private static Tag013 uniqueInstance;

	private Tag013() {
		initialize();
	}

	public static Tag013 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag013();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "013";
		label = "Patent Control Information";
		mqTag = "PatentControl";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Number", "NR",
			"b", "Country", "NR",
			"c", "Type of number", "NR",
			"d", "Date", "R",
			"e", "Status", "R",
			"f", "Party to document", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		// TODO
		// $f - Codes from: MARC Code List for Countries and MARC Code List for Organizations.

		getSubfield("a").setMqTag("number");
		getSubfield("b").setMqTag("country");
		getSubfield("c").setMqTag("type");
		getSubfield("d").setMqTag("date");
		getSubfield("e").setMqTag("status");
		getSubfield("f").setMqTag("partyToDocument");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
