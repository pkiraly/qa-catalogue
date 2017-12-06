package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#903
 */
public class Tag903 extends DataFieldDefinition {

	private static Tag903 uniqueInstance;

	private Tag903() {
		initialize();
		postCreation();
	}

	public static Tag903 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag903();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "903";
		label = "KIRJASTOKOHTAINEN KOODI";
		mqTag = "KirjastokohtainenKoodi";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#903";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Koodi", "NR"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
