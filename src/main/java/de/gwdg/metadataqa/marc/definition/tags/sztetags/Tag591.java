package de.gwdg.metadataqa.marc.definition.tags.sztetags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Locally defined field in Gent
 */
public class Tag591 extends DataFieldDefinition {

	private static Tag591 uniqueInstance;

	private Tag591() {
		initialize();
		postCreation();
	}

	public static Tag591 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag591();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "591";
		label = "A beszerzés forrása";
		mqTag = "ABeszerzesForrasa";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "http://vocal.lib.klte.hu/marc/bib/591.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "A megjegyzés szövege", "NR",
			"5", "Az intézmény és példány, amelyre a megjegyzés vonatkozik", "NR"
		);
	}
}
