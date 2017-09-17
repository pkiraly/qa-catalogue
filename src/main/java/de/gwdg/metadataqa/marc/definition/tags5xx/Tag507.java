package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Scale Note for Graphic Material
 * http://www.loc.gov/marc/bibliographic/bd507.html
 */
public class Tag507 extends DataFieldDefinition {

	private static Tag507 uniqueInstance;

	private Tag507() {
		initialize();
	}

	public static Tag507 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag507();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "507";
		label = "Scale Note for Graphic Material";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Representative fraction of scale note", "NR",
				"b", "Remainder of scale note", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
