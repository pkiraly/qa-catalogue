package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Index Term - Uncontrolled
 * http://www.loc.gov/marc/bibliographic/bd653.html,
 */
public class Tag653 extends DataFieldDefinition {

	private static Tag653 uniqueInstance;

	private Tag653() {
		initialize();
	}

	public static Tag653 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag653();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "653";
		label = "Index Term - Uncontrolled";
		bibframeTag = "Subject";
		mqTag = "UncontrolledIndexTerm";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Level of index term").setCodes(
			" ", "No information provided",
			"0", "No level specified",
			"1", "Primary",
			"2", "Secondary"
		);
		ind2 = new Indicator("Type of term or name").setCodes(
			" ", "No information provided",
			"0", "Topical term",
			"1", "Personal name",
			"2", "Corporate name",
			"3", "Meeting name",
			"4", "Chronological term",
			"5", "Geographic name",
			"6", "Genre/form term"
		);
		setSubfieldsWithCardinality(
			"a", "Uncontrolled term", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("a").setMqTag("rdf:value");
		getSubfield("6").setMqTag("linkage");
		getSubfield("8").setMqTag("fieldLink");

	}
}
