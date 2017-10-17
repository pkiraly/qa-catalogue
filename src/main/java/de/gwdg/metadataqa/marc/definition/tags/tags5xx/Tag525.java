package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Supplement Note
 * http://www.loc.gov/marc/bibliographic/bd525.html
 */
public class Tag525 extends DataFieldDefinition {

	private static Tag525 uniqueInstance;

	private Tag525() {
		initialize();
		postCreation();
	}

	public static Tag525 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag525();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "525";
		label = "Supplement Note";
		bibframeTag = "SupplementaryContent";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Supplement note", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
