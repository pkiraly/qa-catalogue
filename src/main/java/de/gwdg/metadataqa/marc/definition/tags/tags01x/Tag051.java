package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Library of Congress Copy, Issue, Offprint Statement
 * http://www.loc.gov/marc/bibliographic/bd051.html
 */
public class Tag051 extends DataFieldDefinition {

	private static Tag051 uniqueInstance;

	private Tag051() {
		initialize();
		postCreation();
	}

	public static Tag051 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag051();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "051";
		label = "Library of Congress Copy, Issue, Offprint Statement";
		bibframeTag = "ShelfMarkLcc";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd051.html";

		ind1 = new Indicator();
		ind2 = new Indicator()
			.setHistoricalCodes(
				"0", "No series involved",
				"1", "Main series",
				"2", "Subseries",
				"3", "Sub-subseries"
			);

		setSubfieldsWithCardinality(
			"a", "Classification number", "NR",
			"b", "Item number", "NR",
			"c", "Copy information", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value");
		getSubfield("b").setMqTag("itemNumber");
		getSubfield("c").setMqTag("copy");
		getSubfield("8").setMqTag("fieldLink");
	}
}
