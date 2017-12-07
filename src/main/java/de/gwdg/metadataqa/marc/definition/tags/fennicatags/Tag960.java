package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#960
 */
public class Tag960 extends DataFieldDefinition {

	private static Tag960 uniqueInstance;

	private Tag960() {
		initialize();
		postCreation();
	}

	public static Tag960 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag960();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "960";
		label = "KOKOELMA";
		mqTag = "Kokoelma";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#960";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Kokoelma", "NR"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
