package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Postal Registration Number
 * http://www.loc.gov/marc/bibliographic/bd032.html
 */
public class Tag032 extends DataFieldDefinition {

	private static Tag032 uniqueInstance;

	private Tag032() {
		initialize();
		postCreation();
	}

	public static Tag032 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag032();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "032";
		label = "Postal Registration Number";
		bibframeTag = "PostalRegistration";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd032.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Postal registration number", "NR",
			"b", "Source agency assigning number", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("rdf:value");
		getSubfield("b").setBibframeTag("source");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
