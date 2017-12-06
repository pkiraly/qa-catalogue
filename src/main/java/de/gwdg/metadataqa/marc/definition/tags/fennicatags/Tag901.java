package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#901
 */
public class Tag901 extends DataFieldDefinition {

	private static Tag901 uniqueInstance;

	private Tag901() {
		initialize();
		postCreation();
	}

	public static Tag901 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag901();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "901";
		label = "KIRJASTOKOHTAINEN KOODI";
		mqTag = "KirjastokohtainenKoodi";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#901";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Koodi", "NR"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
