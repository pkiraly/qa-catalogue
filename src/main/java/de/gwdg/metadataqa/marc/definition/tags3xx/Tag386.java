package de.gwdg.metadataqa.marc.definition.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;

/**
 * Creator/Contributor Characteristics
 * http://www.loc.gov/marc/bibliographic/bd386.html
 */
public class Tag386 extends DataFieldDefinition {

	private static Tag386 uniqueInstance;

	private Tag386() {
		initialize();
	}

	public static Tag386 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag386();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "386";
		label = "Creator/Contributor Characteristics";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator();
		ind2 = new Indicator();
		setSubfieldsWithCardinality(
			"a", "Creator/contributor term", "R",
			"b", "Creator/contributor code", "R",
			"i", "Relationship information", "R",
			"m", "Demographic group term", "NR",
			"n", "Demographic group code", "NR",
			"0", "Authority record control number or standard number", "R",
			"2", "Source", "NR",
			"3", "Materials specified", "NR",
			"4", "Relationship", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
	}
}
