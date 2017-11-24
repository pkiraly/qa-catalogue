package de.gwdg.metadataqa.marc.definition.tags.sztetags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Locally defined field in SZTE
 */
public class Tag598 extends DataFieldDefinition {

	private static Tag598 uniqueInstance;

	private Tag598() {
		initialize();
		postCreation();
	}

	public static Tag598 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag598();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "598";
		label = "Collection";
		mqTag = "Collection";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "http://vocal.lib.klte.hu/marc/bib/596.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Name of the collection", "NR"
		);
	}
}
