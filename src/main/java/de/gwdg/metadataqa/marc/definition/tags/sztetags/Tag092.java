package de.gwdg.metadataqa.marc.definition.tags.sztetags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Locally defined field in Gent
 */
public class Tag092 extends DataFieldDefinition {

	private static Tag092 uniqueInstance;

	private Tag092() {
		initialize();
		postCreation();
	}

	public static Tag092 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag092();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "092";
		label = "Szakjelzet";
		mqTag = "ClassificationNumber";
		cardinality = Cardinality.Nonrepeatable;
		descriptionUrl = "http://vocal.lib.klte.hu/marc/bib/092.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Osztályozási szám", "NR",
			"b", "Cutter", "NR"
		);

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("a").setMqTag("cutter");
	}
}
