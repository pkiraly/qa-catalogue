package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * General Note
 * http://www.loc.gov/marc/bibliographic/bd500.html
 */
public class Tag500 extends DataFieldDefinition {

	private static Tag500 uniqueInstance;

	private Tag500(){
		initialize();
	}

	public static Tag500 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag500();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "500";
		label = "General Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("").setCodes();
		setSubfieldsWithCardinality(
				"a", "General note", "NR",
				"3", "Materials specified", "NR",
				"5", "Institution to which field applies", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
