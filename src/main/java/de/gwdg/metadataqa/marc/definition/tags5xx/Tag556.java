package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Information About Documentation Note
 * http://www.loc.gov/marc/bibliographic/bd556.html
 */
public class Tag556 extends DataFieldDefinition {

	private static Tag556 uniqueInstance;

	private Tag556() {
		initialize();
	}

	public static Tag556 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag556();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "556";
		label = "Information About Documentation Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Display constant controller").setCodes(
			" ", "Documentation",
			"8", "No display constant generated"
		);
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"a", "Information about documentation note", "NR",
			"z", "International Standard Book Number", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
	}
}
