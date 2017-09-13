package de.gwdg.metadataqa.marc.definition.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Edition Statement
 * http://www.loc.gov/marc/bibliographic/bd250.html
 */
public class Tag250 extends DataFieldDefinition {
	private static Tag250 uniqueInstance;

	private Tag250() {
		initialize();
	}

	public static Tag250 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag250();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "250";
		label = "Edition Statement";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Edition statement", "NR",
				"b", "Remainder of edition statement", "NR",
				"3", "Materials specified", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
