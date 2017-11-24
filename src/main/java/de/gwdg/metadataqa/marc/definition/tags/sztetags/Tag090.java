package de.gwdg.metadataqa.marc.definition.tags.sztetags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Locally defined field in Gent
 */
public class Tag090 extends DataFieldDefinition {

	private static Tag090 uniqueInstance;

	private Tag090() {
		initialize();
		postCreation();
	}

	public static Tag090 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag090();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "090";
		label = "Szakjelzet, MTA Matematikai Intézet";
		mqTag = "ClassificationNumber";
		cardinality = Cardinality.Nonrepeatable;
		descriptionUrl = "http://vocal.lib.klte.hu/marc/bib/090.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Osztályozási szám", "R"
		);

		getSubfield("a").setMqTag("rdf:value");
	}
}
