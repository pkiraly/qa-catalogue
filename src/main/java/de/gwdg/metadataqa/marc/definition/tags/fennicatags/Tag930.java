package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#930
 */
public class Tag930 extends DataFieldDefinition {

	private static Tag930 uniqueInstance;

	private Tag930() {
		initialize();
		postCreation();
	}

	public static Tag930 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag930();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "930";
		label = "KOMITEAN ASETTAMISPÄIVÄ - VOYAGER-KENTTÄ";
		mqTag = "KomiteanAsettamispäivä";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#930";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Komitean asettamispäivä", "NR"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
