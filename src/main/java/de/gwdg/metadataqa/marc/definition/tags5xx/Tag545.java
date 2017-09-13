package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Biographical or Historical Data
 * http://www.loc.gov/marc/bibliographic/bd545.html
 */
public class Tag545 extends DataFieldDefinition {

	private static Tag545 uniqueInstance;

	private Tag545() {
		initialize();
	}

	public static Tag545 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag545();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "545";
		label = "Biographical or Historical Data";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Type of data").setCodes(
				" ", "No information provided",
				"0", "Biographical sketch",
				"1", "Administrative history"
		);
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Biographical or historical data", "NR",
				"b", "Expansion", "NR",
				"u", "Uniform Resource Identifier", "R",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
