package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Character Sets Present
 * http://www.loc.gov/marc/bibliographic/bd066.html
 */
public class Tag066 extends DataFieldDefinition {

	private static Tag066 uniqueInstance;

	private Tag066() {
		initialize();
	}

	public static Tag066 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag066();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "066";
		label = "Character Sets Present";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Primary G0 character set", "NR",
				"b", "Primary G1 character set", "NR",
				"c", "Alternate G0 or G1 character set", "R"
				// "6", "Linkage", "NR",
				// "8", "Field link and sequence number", "R"
		);
	}
}
