package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Citation/References Note
 * http://www.loc.gov/marc/bibliographic/bd510.html
 */
public class Tag510 extends DataFieldDefinition {

	private static Tag510 uniqueInstance;

	private Tag510(){
		initialize();
	}

	public static Tag510 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag510();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "510";
		label = "Citation/References Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Coverage/location in source").setCodes(
				"0", "Coverage unknown",
				"1", "Coverage complete",
				"2", "Coverage is selective",
				"3", "Location in source not given",
				"4", "Location in source given"
		);
		ind2 = new Indicator("");
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
	}
}
