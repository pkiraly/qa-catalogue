package de.gwdg.metadataqa.marc.definition.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * http://www.loc.gov/marc/bibliographic/bd300.html
 */
public class Tag300 extends DataFieldDefinition {
	private static Tag300 uniqueInstance;

	private Tag300(){
		initialize();
	}

	public static Tag300 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag300();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "300";
		label = "Physical Description";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("").setCodes();
		ind2 = new Indicator("").setCodes();
		setSubfieldsWithCardinality(
				"a", "Extent", "R",
				"b", "Other physical details", "NR",
				"c", "Dimensions", "R",
				"e", "Accompanying material", "NR",
				"f", "Type of unit", "R",
				"g", "Size of unit", "R",
				"3", "Materials specified", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
		// TODO: "1-9" in ind2 is regex!
	}}
