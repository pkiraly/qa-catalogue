package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * National Agricultural Library Call Number
 * http://www.loc.gov/marc/bibliographic/bd070.html
 */
public class Tag070 extends DataFieldDefinition {

	private static Tag070 uniqueInstance;

	private Tag070() {
		initialize();
	}

	public static Tag070 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag070();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "070";
		label = "National Agricultural Library Call Number";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Existence in NAL collection").setCodes(
				"0", "Item is in NAL",
				"1", "Item is not in NAL"
		);
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Classification number", "R",
				"b", "Item number", "NR",
				// "6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
