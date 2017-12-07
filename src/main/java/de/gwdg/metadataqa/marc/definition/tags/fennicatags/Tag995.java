package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#995
 */
public class Tag995 extends DataFieldDefinition {

	private static Tag995 uniqueInstance;

	private Tag995() {
		initialize();
		postCreation();
	}

	public static Tag995 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag995();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "995";
		label = "ARTON TIEDONTUOTTAJATUNNUS";
		mqTag = "ArtonTiedontuottajatunnus";
		cardinality = Cardinality.Nonrepeatable;
		descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#995";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "ARTOn tiedontuottajatunnus", "R"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
