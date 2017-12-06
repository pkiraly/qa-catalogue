package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#904
 */
public class Tag904 extends DataFieldDefinition {

	private static Tag904 uniqueInstance;

	private Tag904() {
		initialize();
		postCreation();
	}

	public static Tag904 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag904();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "904";
		label = "PIENPAINATEKOODI";
		mqTag = "Pienpainatekoodi";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#904";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Koodi", "NR"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
