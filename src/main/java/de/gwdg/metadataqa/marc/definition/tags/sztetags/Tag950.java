package de.gwdg.metadataqa.marc.definition.tags.sztetags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Locally defined field in SZTE
 */
public class Tag950 extends DataFieldDefinition {

	private static Tag950 uniqueInstance;

	private Tag950() {
		initialize();
		postCreation();
	}

	public static Tag950 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag950();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "950";
		label = "Digitalization job";
		mqTag = "DigitalizationJob";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"f", "Designation", "NR",
			"x", "ID", "NR",
			"v", "Volume", "NR",
			"1", "First page", "NR",
			"2", "Last page", "NR",
			"z", "Publication type", "NR"
		);
	}
}
