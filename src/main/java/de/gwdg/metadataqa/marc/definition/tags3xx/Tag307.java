package de.gwdg.metadataqa.marc.definition.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Hours, etc.
 * http://www.loc.gov/marc/bibliographic/bd307.html
 */
public class Tag307 extends DataFieldDefinition {
	private static Tag307 uniqueInstance;

	private Tag307(){
		initialize();
	}

	public static Tag307 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag307();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "307";
		label = "Hours, etc.";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Display constant controller").setCodes(
				" ", "Hours",
				"8", "No display constant generated"
		);
		ind2 = new Indicator("").setCodes();
		setSubfieldsWithCardinality(
				"a", "Hours", "NR",
				"b", "Additional information", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
		// TODO: $a is complex := <days of the week>, <hours>[;]
		// ';' if there is $b
		// <days of the week> :=
		// Sunday 	Su
		// Monday 	M
		// Tuesday 	Tu
		// Wednesday 	W
		// Thursday 	Th
		// Friday 	F
		// Saturday 	Sa
	}
}
