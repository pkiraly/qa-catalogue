package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Binding Information
 * http://www.loc.gov/marc/bibliographic/bd563.html
 */
public class Tag563 extends DataFieldDefinition {

	private static Tag563 uniqueInstance;

	private Tag563() {
		initialize();
		postCreation();
	}

	public static Tag563 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag563();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "563";
		label = "Binding Information";
		mqTag = "Binding";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Binding note", "NR",
			"u", "Uniform Resource Identifier", "R",
			"3", "Materials specified", "NR",
			"5", "Institution to which field applies", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value");
		getSubfield("u").setMqTag("uri");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("5").setMqTag("institutionToWhichFieldApplies");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
