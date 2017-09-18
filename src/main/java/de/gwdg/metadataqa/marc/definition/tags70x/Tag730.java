package de.gwdg.metadataqa.marc.definition.tags70x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Added Entry - Uniform Title
 * http://www.loc.gov/marc/bibliographic/bd730.html
 */
public class Tag730 extends DataFieldDefinition {

	private static Tag730 uniqueInstance;

	private Tag730() {
		initialize();
	}

	public static Tag730 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag730();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "730";
		label = "Added Entry - Uniform Title";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Nonfiling characters").setCodes(
			"0-9", "Number of nonfiling characters"
		);
		ind1.getCode("0-9").setRange(true);
		ind2 = new Indicator("Type of added entry").setCodes(
			" ", "No information provided",
			"2", "Analytical entry"
		);
		setSubfieldsWithCardinality(
			"a", "Uniform title", "NR",
			"d", "Date of treaty signing", "R",
			"f", "Date of a work", "NR",
			"g", "Miscellaneous information", "R",
			"h", "Medium", "NR",
			"i", "Relationship information", "R",
			"k", "Form subheading", "R",
			"l", "Language of a work", "NR",
			"m", "Medium of performance for music", "R",
			"n", "Number of part/section of a work", "R",
			"o", "Arranged statement for music", "NR",
			"p", "Name of part/section of a work", "R",
			"r", "Key for music", "NR",
			"s", "Version", "NR",
			"t", "Title of a work", "NR",
			"x", "International Standard Serial Number", "NR",
			"0", "Authority record control number or standard number", "R",
			"3", "Materials specified", "NR",
			"5", "Institution to which field applies", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
	}
}
