package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

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
		postCreation();
	}

	public static Tag507 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag507();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "507";
		label = "Scale Note for Graphic Material";
		mqTag = "Scale";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd507.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
				"a", "Representative fraction of scale note", "NR",
				"b", "Remainder of scale note", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("b").setMqTag("remainder");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
