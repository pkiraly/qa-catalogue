package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Cumulative Index/Finding Aids Note
 * http://www.loc.gov/marc/bibliographic/bd555.html
 */
public class Tag555 extends DataFieldDefinition {

	private static Tag555 uniqueInstance;

	private Tag555() {
		initialize();
	}

	public static Tag555 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag555();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "555";
		label = "Cumulative Index/Finding Aids Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Display constant controller").setCodes(
			" ", "Indexes",
			"0", "Finding aids",
			"8", "No display constant generated"
		);
		ind2 = new Indicator();
		setSubfieldsWithCardinality(
			"a", "Cumulative index/finding aids note", "NR",
			"b", "Availability source", "R",
			"c", "Degree of control", "NR",
			"d", "Bibliographic reference", "NR",
			"u", "Uniform Resource Identifier", "R",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
	}
}
