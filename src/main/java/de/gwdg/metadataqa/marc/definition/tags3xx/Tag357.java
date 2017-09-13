package de.gwdg.metadataqa.marc.definition.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Originator Dissemination Control
 * http://www.loc.gov/marc/bibliographic/bd357.html
 */
public class Tag357 extends DataFieldDefinition {
	private static Tag357 uniqueInstance;

	private Tag357() {
		initialize();
	}

	public static Tag357 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag357();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "357";
		label = "Originator Dissemination Control";
		cardinality = Cardinality.Nonrepeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Originator control term", "NR",
				"b", "Originating agency", "R",
				"c", "Authorized recipients of material", "R",
				"g", "Other restrictions", "R",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
