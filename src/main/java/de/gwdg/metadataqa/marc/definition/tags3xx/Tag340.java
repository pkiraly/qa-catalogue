package de.gwdg.metadataqa.marc.definition.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Physical Medium
 * http://www.loc.gov/marc/bibliographic/bd340.html
 */
public class Tag340 extends DataFieldDefinition {
	private static Tag340 uniqueInstance;

	private Tag340(){
		initialize();
	}

	public static Tag340 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag340();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "340";
		label = "Physical Medium";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Material base and configuration", "R",
				"b", "Dimensions", "R",
				"c", "Materials applied to surface", "R",
				"d", "Information recording technique", "R",
				"e", "Support", "R",
				"f", "Production rate/ratio", "R",
				"g", "Color content", "R",
				"h", "Location within medium", "R",
				"i", "Technical specifications of medium", "R",
				"j", "Generation", "R",
				"k", "Layout", "R",
				"m", "Book format", "R",
				"n", "Font size", "R",
				"o", "Polarity", "R",
				"0", "Authority record control number or standard number", "R",
				"2", "Source", "NR",
				"3", "Materials specified", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
