package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Binding Information
 * http://www.loc.gov/marc/bibliographic/bd563.html
 */
public class Tag563 extends DataFieldDefinition {

	private static Tag563 uniqueInstance;

	private Tag563() {
		initialize();
	}

	public static Tag563 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag563();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "563";
		label = "Binding Information";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"a", "Binding note", "NR",
			"u", "Uniform Resource Identifier", "R",
			"3", "Materials specified", "NR",
			"5", "Institution to which field applies", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
	}
}
