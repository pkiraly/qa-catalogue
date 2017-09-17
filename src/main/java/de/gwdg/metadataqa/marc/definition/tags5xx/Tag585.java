package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Exhibitions Note
 * http://www.loc.gov/marc/bibliographic/bd585.html
 */
public class Tag585 extends DataFieldDefinition {

	private static Tag585 uniqueInstance;

	private Tag585() {
		initialize();
	}

	public static Tag585 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag585();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "585";
		label = "Exhibitions Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"a", "Exhibitions note", "NR",
			"3", "Materials specified", "NR",
			"5", "Institution to which field applies", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
	}
}
