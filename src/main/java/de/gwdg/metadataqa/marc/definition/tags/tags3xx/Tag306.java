package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Playing Time
 * http://www.loc.gov/marc/bibliographic/bd306.html
 */
public class Tag306 extends DataFieldDefinition {
	private static Tag306 uniqueInstance;

	private Tag306() {
		initialize();
		postCreation();
	}

	public static Tag306 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag306();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "306";
		label = "Playing Time";
		bibframeTag = "Duration";
		cardinality = Cardinality.Nonrepeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Playing time", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		// TODO: $a is regex: hhmmss

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("6").setMqTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
