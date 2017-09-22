package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Former Title Complexity Note
 * http://www.loc.gov/marc/bibliographic/bd547.html
 */
public class Tag547 extends DataFieldDefinition {

	private static Tag547 uniqueInstance;

	private Tag547() {
		initialize();
	}

	public static Tag547 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag547();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "547";
		label = "Former Title Complexity Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator();
		ind2 = new Indicator();
		setSubfieldsWithCardinality(
			"a", "Former title complexity note", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
	}
}
