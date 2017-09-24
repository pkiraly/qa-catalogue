package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * General Note
 * http://www.loc.gov/marc/bibliographic/bd500.html
 */
public class Tag500 extends DataFieldDefinition {

	private static Tag500 uniqueInstance;

	private Tag500() {
		initialize();
	}

	public static Tag500 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag500();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "500";
		label = "General Note";
		bibframeTag = "Note";
		mqTag = "GeneralNote";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "General note", "NR",
			"3", "Materials specified", "NR",
			"5", "Institution to which field applies", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("5").setMqTag("institutionToWhichFieldApplies");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
