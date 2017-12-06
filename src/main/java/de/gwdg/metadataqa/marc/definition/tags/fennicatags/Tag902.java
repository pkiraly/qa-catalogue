package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#902
 */
public class Tag902 extends DataFieldDefinition {

	private static Tag902 uniqueInstance;

	private Tag902() {
		initialize();
		postCreation();
	}

	public static Tag902 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag902();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "902";
		label = "PAINOSKOODI";
		mqTag = "Painoskoodi";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#902";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Koodi", "NR"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
