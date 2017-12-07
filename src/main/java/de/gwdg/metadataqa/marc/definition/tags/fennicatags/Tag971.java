package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#971
 */
public class Tag971 extends DataFieldDefinition {

	private static Tag971 uniqueInstance;

	private Tag971() {
		initialize();
		postCreation();
	}

	public static Tag971 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag971();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "971";
		label = "KAUPALLISET TIEDOT";
		mqTag = "KaupallisetTiedot";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#971";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Tiedoston tuottaja", "NR",
			"b", "Tiedoston tunnus", "NR",
			"c", "Viimeinen tilauspäivä", "NR",
			"d", "Tiedoston (listan) nimi ja numero", "NR",
			"e", "Hinta ilman ALVia", "NR",
			"f", "ALV-prosentti", "NR",
			"g", "Myyntialennus", "NR",
			"h", "Erikoishinta", "NR",
			"i", "Sidontakoodi", "NR",
			"k", "Käyttöoikeus (videot)", "NR",
			"l", "Tekstitys (videot)", "NR",
			"m", "Koodi", "NR",
			"p", "Sidonnan hinta", "NR",
			"s", "Järjestyskoodi listalla", "NR",
			"t", "Tuoteryhmä", "NR",
			"u", "Lisätiedot, URL", "NR",
			"x", "Lisätiedot, tyyppi", "NR"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
