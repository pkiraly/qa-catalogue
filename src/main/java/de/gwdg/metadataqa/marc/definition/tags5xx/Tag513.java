package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Type of Report and Period Covered Note
 * http://www.loc.gov/marc/bibliographic/bd513.html
 */
public class Tag513 extends DataFieldDefinition {

	private static Tag513 uniqueInstance;

	private Tag513(){
		initialize();
	}

	public static Tag513 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag513();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "513";
		label = "Type of Report and Period Covered Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Type of report", "NR",
				"b", "Period covered", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
