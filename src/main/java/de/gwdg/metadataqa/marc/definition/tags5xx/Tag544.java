package de.gwdg.metadataqa.marc.definition.tags5xx;

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
	}

	public static Tag544 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag544();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "544";
		label = "Location of Other Archival Materials Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Relationship").setCodes(
			" ", "No information provided",
			"0", "Associated materials",
			"1", "Related materials"
		);
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
	}
}
