package de.gwdg.metadataqa.marc.definition.tags.sztetags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Locally defined field in Gent
 */
public class Tag596 extends DataFieldDefinition {

	private static Tag596 uniqueInstance;

	private Tag596() {
		initialize();
		postCreation();
	}

	public static Tag596 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag596();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "596";
		label = "A dokumentum állapota";
		mqTag = "ADokumentumAllapota";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "http://vocal.lib.klte.hu/marc/bib/596.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "A restaurálás ténye", "NR",
			"b", "A dokumentum állapota", "NR",
			"5", "Az intézmény és példány, amelyre a megjegyzés vonatkozik", "NR"
		);
	}
}
