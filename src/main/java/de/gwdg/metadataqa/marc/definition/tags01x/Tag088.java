package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Report Number
 * http://www.loc.gov/marc/bibliographic/bd088.html
 */
public class Tag088 extends DataFieldDefinition {

	private static Tag088 uniqueInstance;

	private Tag088() {
		initialize();
	}

	public static Tag088 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag088();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "088";
		label = "Report Number";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Report number", "NR",
				"z", "Canceled/invalid report number", "R",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
