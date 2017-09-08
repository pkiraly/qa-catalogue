package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Source of Acquisition
 * http://www.loc.gov/marc/bibliographic/bd037.html
 */
public class Tag037 extends DataFieldDefinition {

	private static Tag037 uniqueInstance;

	private Tag037() {
		initialize();
	}

	public static Tag037 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag037();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "037";
		label = "Source of Acquisition";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Source of acquisition sequence").setCodes(
				" ", "Not applicable/No information provided/Earliest",
				"2", "Intervening",
				"3", "Current/Latest"
		);
		ind2 = new Indicator("").setCodes();
		setSubfieldsWithCardinality(
				"a", "Stock number", "NR",
				"b", "Source of stock number/acquisition", "NR",
				"c", "Terms of availability", "R",
				"f", "Form of issue", "R",
				"g", "Additional format characteristics", "R",
				"n", "Note", "R",
				"3", "Materials specified", "NR",
				"5", "Institution to which field applies", "R",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
