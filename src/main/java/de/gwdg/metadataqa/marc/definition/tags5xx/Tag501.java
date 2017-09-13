package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * With Note
 * http://www.loc.gov/marc/bibliographic/bd501.html
 */
public class Tag501 extends DataFieldDefinition {

	private static Tag501 uniqueInstance;

	private Tag501() {
		initialize();
	}

	public static Tag501 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag501();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "501";
		label = "With Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "With note", "NR",
				"5", "Institution to which field applies", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
