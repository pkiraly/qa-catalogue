package de.gwdg.metadataqa.marc.definition.tags.sztetags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Locally defined field in Gent
 */
public class Tag093 extends DataFieldDefinition {

	private static Tag093 uniqueInstance;

	private Tag093() {
		initialize();
		postCreation();
	}

	public static Tag093 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag093();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "093";
		label = "Új Könyvek azonosító";
		mqTag = "UKIdentifier";
		cardinality = Cardinality.Nonrepeatable;
		descriptionUrl = "http://vocal.lib.klte.hu/marc/bib/093.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Azonosító szám", "R"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
