package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Source of Description Note
 * http://www.loc.gov/marc/bibliographic/bd588.html
 */
public class Tag588 extends DataFieldDefinition {

	private static Tag588 uniqueInstance;

	private Tag588() {
		initialize();
		postCreation();
	}

	public static Tag588 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag588();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "588";
		label = "Source of Description Note";
		mqTag = "SourceOfDescription";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator("Display constant controller")
			.setCodes(
				" ", "No information provided",
				"0", "Source of description",
				"1", "Latest issue consulted"
			)
			.setMqTag("displayConstant");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Source of description note", "NR",
			"5", "Institution to which field applies", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value");
		getSubfield("5").setMqTag("institutionToWhichFieldApplies");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
