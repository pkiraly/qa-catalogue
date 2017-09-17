package de.gwdg.metadataqa.marc.definition.tags20x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * http://www.loc.gov/marc/bibliographic/bd245.html
 */
public class Tag245 extends DataFieldDefinition {
	private static Tag245 uniqueInstance;

	private Tag245() {
		initialize();
	}

	public static Tag245 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag245();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "245";
		label = "Title Statement";
		cardinality = Cardinality.Nonrepeatable;
		ind1 = new Indicator("Title added entry").setCodes(
			"0", "No added entry",
			"1", "Added entry"
		);
		ind2 = new Indicator("Nonfiling characters").setCodes(
			"0", "No nonfiling characters",
			"1-9", "Number of nonfiling characters"
		);
		ind2.getCode("1-9").setRange(true);
		setSubfieldsWithCardinality(
			"a", "Title", "NR",
			"b", "Remainder of title", "NR",
			"c", "Statement of responsibility, etc.", "NR",
			"f", "Inclusive dates", "NR",
			"g", "Bulk dates", "NR",
			"h", "Medium", "NR",
			"k", "Form", "R",
			"n", "Number of part/section of a work", "R",
			"p", "Name of part/section of a work", "R",
			"s", "Version", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
	}
}
