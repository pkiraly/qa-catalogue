package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Library of Congress Control Number
 * http://www.loc.gov/marc/bibliographic/bd010.html
 */
public class Tag010 extends DataFieldDefinition {

	private static Tag010 uniqueInstance;

	private Tag010() {
		initialize();
	}

	public static Tag010 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag010();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "010";
		label = "Library of Congress Control Number";
		bibframeTag = "identifiedBy/Lccn";
		cardinality = Cardinality.Nonrepeatable;
		ind1 = new Indicator("").setCodes(" ", "Undefined");
		ind2 = new Indicator("").setCodes(" ", "Undefined");
		setSubfieldsWithCardinality(
			"a", "LC control number", "NR",
			"b", "NUCMC control number", "R",
			"z", "Canceled/invalid LC control number", "R",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("a").setBibframeTag("rdf:value");
	}
}
