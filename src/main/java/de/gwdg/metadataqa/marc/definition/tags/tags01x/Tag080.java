package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Universal Decimal Classification Number
 * http://www.loc.gov/marc/bibliographic/bd080.html
 */
public class Tag080 extends DataFieldDefinition {

	private static Tag080 uniqueInstance;

	private Tag080() {
		initialize();
		postCreation();
	}

	public static Tag080 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag080();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "080";
		label = "Universal Decimal Classification Number";
		mqTag = "Udc";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd080.html";

		ind1 = new Indicator("Type of edition")
			.setCodes(
				" ", "No information provided",
				"0", "Full",
				"1", "Abridged"
			)
			.setMqTag("type");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Universal Decimal Classification number", "NR",
			"b", "Item number", "NR",
			"x", "Common auxiliary subdivision", "R",
			"2", "Edition identifier", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("b").setMqTag("number");
		getSubfield("x").setMqTag("commonAuxiliarySubdivision");
		getSubfield("2").setMqTag("edition");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
