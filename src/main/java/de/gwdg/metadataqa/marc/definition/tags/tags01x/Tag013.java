package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Patent Control Information
 * http://www.loc.gov/marc/bibliographic/bd013.html
 */
public class Tag013 extends DataFieldDefinition {

	private static Tag013 uniqueInstance;

	private Tag013() {
		initialize();
		postCreation();
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
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd013.html";

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

		getSubfield("b").setCodeList(CountryCodes.getInstance());
		// TODO
		// $f - Codes from: MARC Code List for Countries and MARC Code List for Organizations.
		getSubfield("f").setCodeList(OrganizationCodes.getInstance());
		getSubfield("6").setContentParser(LinkageParser.getInstance());

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
