package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Musical Incipits Information
 * http://www.loc.gov/marc/bibliographic/bd031.html
 */
public class Tag035 extends DataFieldDefinition {

	private static Tag035 uniqueInstance;

	private Tag035() {
		initialize();
	}

	public static Tag035 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag035();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "035";
		bibframeTag = "systemControlNumber";
		label = "System Control Number";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("").setCodes();
		ind2 = new Indicator("").setCodes();
		setSubfieldsWithCardinality(
				"a", "System control number", "NR",
				"z", "Canceled/invalid control number", "R",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
