package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag013 extends DataFieldDefinition {

	private static Tag013 uniqueInstance;

	private Tag013(){
		initialize();
	}

	public static Tag013 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag013();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "013";
		label = "Patent Control Information";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("").setCodes(" ", "Undefined");
		ind2 = new Indicator("").setCodes(" ", "Undefined");
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
	}
}
