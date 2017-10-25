package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Linking Entry Complexity Note
 * http://www.loc.gov/marc/bibliographic/bd580.html
 */
public class Tag580 extends DataFieldDefinition {

	private static Tag580 uniqueInstance;

	private Tag580() {
		initialize();
		postCreation();
	}

	public static Tag580 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag580();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "580";
		label = "Linking Entry Complexity Note";
		mqTag = "LinkingEntryComplexity";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Linking entry complexity note", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");

		setHistoricalSubfields(
			"z", "Source of note information [OBSOLETE, 1990]"
		);
	}
}
