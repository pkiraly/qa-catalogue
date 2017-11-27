package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Target Audience Note
 * http://www.loc.gov/marc/bibliographic/bd521.html
 */
public class Tag521 extends DataFieldDefinition {

	private static Tag521 uniqueInstance;

	private Tag521() {
		initialize();
		postCreation();
	}

	public static Tag521 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag521();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "521";
		label = "Target Audience Note";
		bibframeTag = "IntendedAudience";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd521.html";

		ind1 = new Indicator("Display constant controller")
			.setCodes(
				" ", "Audience",
				"0", "Reading grade level",
				"1", "Interest age level",
				"2", "Interest grade level",
				"3", "Special audience characteristics",
				"4", "Motivation/interest level",
				"8", "No display constant generated"
			)
			.setMqTag("displayConstant");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Target audience note", "R",
			"b", "Source", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value");
		getSubfield("b").setBibframeTag("source");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
