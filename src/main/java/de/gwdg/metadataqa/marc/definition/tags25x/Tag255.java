package de.gwdg.metadataqa.marc.definition.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Cartographic Mathematical Data
 * http://www.loc.gov/marc/bibliographic/bd255.html
 */
public class Tag255 extends DataFieldDefinition {
	private static Tag255 uniqueInstance;

	private Tag255() {
		initialize();
	}

	public static Tag255 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag255();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "255";
		label = "Cartographic Mathematical Data";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Statement of scale", "NR",
				"b", "Statement of projection", "NR",
				"c", "Statement of coordinates", "NR",
				"d", "Statement of zone", "NR",
				"e", "Statement of equinox", "NR",
				"f", "Outer G-ring coordinate pairs", "NR",
				"g", "Exclusion G-ring coordinate pairs", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
