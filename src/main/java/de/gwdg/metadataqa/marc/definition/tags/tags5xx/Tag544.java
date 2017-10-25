package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Location of Other Archival Materials Note
 * http://www.loc.gov/marc/bibliographic/bd544.html
 */
public class Tag544 extends DataFieldDefinition {

	private static Tag544 uniqueInstance;

	private Tag544() {
		initialize();
		postCreation();
	}

	public static Tag544 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag544();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "544";
		label = "Location of Other Archival Materials Note";
		mqTag = "LocationOfOtherArchivalMaterials";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator("Relationship")
			.setCodes(
				" ", "No information provided",
				"0", "Associated materials",
				"1", "Related materials"
			)
			.setMqTag("relationship");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Custodian", "R",
			"b", "Address", "R",
			"c", "Country", "R",
			"d", "Title", "R",
			"e", "Provenance", "R",
			"n", "Note", "R",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setMqTag("custodian");
		getSubfield("b").setMqTag("address");
		getSubfield("c").setMqTag("country");
		getSubfield("d").setMqTag("title");
		getSubfield("e").setMqTag("provenance");
		getSubfield("n").setMqTag("note");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
