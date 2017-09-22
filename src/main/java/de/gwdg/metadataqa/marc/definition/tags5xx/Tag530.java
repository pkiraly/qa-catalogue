package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Additional Physical Form available Note
 * http://www.loc.gov/marc/bibliographic/bd530.html
 */
public class Tag530 extends DataFieldDefinition {

	private static Tag530 uniqueInstance;

	private Tag530() {
		initialize();
	}

	public static Tag530 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag530();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "530";
		label = "Additional Physical Form available Note";
		mqTag = "AdditionalPhysicalFormAvailable";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Additional physical form available note", "NR",
			"b", "Availability source", "NR",
			"c", "Availability conditions", "NR",
			"d", "Order number", "NR",
			"u", "Uniform Resource Identifier", "R",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("b").setMqTag("source");
		getSubfield("c").setMqTag("conditions");
		getSubfield("d").setMqTag("orderNumber");
		getSubfield("u").setMqTag("uri");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
