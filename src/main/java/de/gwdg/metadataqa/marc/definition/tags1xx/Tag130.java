package de.gwdg.metadataqa.marc.definition.tags1xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Main Entry - Uniform Title
 * https://www.loc.gov/marc/bibliographic/bd130.html
 */
public class Tag130 extends DataFieldDefinition {

	private static Tag130 uniqueInstance;

	private Tag130() {
		initialize();
	}

	public static Tag130 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag130();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "130";
		label = "Main Entry - Uniform Title";
		cardinality = Cardinality.Nonrepeatable;

		ind1 = new Indicator("Nonfiling characters").setCodes(
			"0-9", "Number of nonfiling characters"
		);
		ind1.getCode("0-9").setRange(true);
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"a", "Uniform title", "NR",
			"d", "Date of treaty signing", "R",
			"f", "Date of a work", "NR",
			"g", "Miscellaneous information", "R",
			"h", "Medium", "NR",
			"k", "Form subheading", "R",
			"l", "Language of a work", "NR",
			"m", "Medium of performance for music", "R",
			"n", "Number of part/section of a work", "R",
			"o", "Arranged statement for music", "NR",
			"p", "Name of part/section of a work", "R",
			"r", "Key for music", "NR",
			"s", "Version", "NR",
			"t", "Title of a work", "NR",
			"0", "Authority record control number or standard number", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
	}
}
