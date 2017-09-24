package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Ownership and Custodial History
 * http://www.loc.gov/marc/bibliographic/bd561.html
 */
public class Tag561 extends DataFieldDefinition {

	private static Tag561 uniqueInstance;

	private Tag561() {
		initialize();
	}

	public static Tag561 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag561();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "561";
		label = "Ownership and Custodial History";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Privacy").setCodes(
			" ", "No information provided",
			"0", "Private",
			"1", "Not private"
		);
		ind2 = new Indicator();
		setSubfieldsWithCardinality(
			"a", "History", "NR",
			"u", "Uniform Resource Identifier", "R",
			"3", "Materials specified", "NR",
			"5", "Institution to which field applies", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
	}
}
