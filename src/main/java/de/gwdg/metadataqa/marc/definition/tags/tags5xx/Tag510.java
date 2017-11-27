package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Citation/References Note
 * http://www.loc.gov/marc/bibliographic/bd510.html
 */
public class Tag510 extends DataFieldDefinition {

	private static Tag510 uniqueInstance;

	private Tag510() {
		initialize();
		postCreation();
	}

	public static Tag510 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag510();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "510";
		label = "Citation/References Note";
		mqTag = "Citation";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd510.html";

		ind1 = new Indicator("Coverage/location in source")
			.setCodes(
				"0", "Coverage unknown",
				"1", "Coverage complete",
				"2", "Coverage is selective",
				"3", "Location in source not given",
				"4", "Location in source given"
			)
			.setMqTag("coverageOrLocationInSource");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Name of source", "NR",
			"b", "Coverage of source", "NR",
			"c", "Location within source", "NR",
			"u", "Uniform Resource Identifier", "R",
			"x", "International Standard Serial Number", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setMqTag("name");
		getSubfield("b").setMqTag("coverage");
		getSubfield("c").setMqTag("location");
		getSubfield("u").setMqTag("uri");
		getSubfield("x").setMqTag("issn");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
