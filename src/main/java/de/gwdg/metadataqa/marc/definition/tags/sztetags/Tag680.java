package de.gwdg.metadataqa.marc.definition.tags.sztetags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Locally defined field in SZTE
 */
public class Tag680 extends DataFieldDefinition {

	private static Tag680 uniqueInstance;

	private Tag680() {
		initialize();
		postCreation();
	}

	public static Tag680 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag680();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "680";
		label = "ETO solution";
		mqTag = "UDCsolution";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "http://vocal.lib.klte.hu/marc/bib/680.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "ETO solution", "R",
			"x", "General subdivision", "R",
			"y", "Chronological subdivision", "R",
			"z", "Geographical subdivision", "R"
		);
	}
}
