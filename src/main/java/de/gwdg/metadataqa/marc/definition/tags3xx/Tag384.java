package de.gwdg.metadataqa.marc.definition.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Key
 * http://www.loc.gov/marc/bibliographic/bd384.html
 */
public class Tag384 extends DataFieldDefinition {
	private static Tag384 uniqueInstance;

	private Tag384(){
		initialize();
	}

	public static Tag384 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag384();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "384";
		label = "Key";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Key type").setCodes(
				" ", "Relationship to original unknown",
				"0", "Original key",
				"1", "Transposed key "
		);
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Key", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
