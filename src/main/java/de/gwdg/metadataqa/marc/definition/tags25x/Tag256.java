package de.gwdg.metadataqa.marc.definition.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Computer File Characteristics
 * http://www.loc.gov/marc/bibliographic/bd256.html
 */
public class Tag256 extends DataFieldDefinition {
	private static Tag256 uniqueInstance;

	private Tag256() {
		initialize();
	}

	public static Tag256 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag256();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "256";
		label = "Computer File Characteristics";
		cardinality = Cardinality.Nonrepeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"a", "Computer file characteristics", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
	}
}
