package de.gwdg.metadataqa.marc.definition.tags80x;

import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Series Added Entry - Corporate Name
 * http://www.loc.gov/marc/bibliographic/bd810.html
 */
public class Tag810 extends DataFieldDefinition {

	private static Tag810 uniqueInstance;

	private Tag810() {
		initialize();
	}

	public static Tag810 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag810();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "810";
		label = "Series Added Entry - Corporate Name";
		ind1 = new Indicator("Type of corporate name entry element").setCodes(
			"0", "Inverted name",
			"1", "Jurisdiction name",
			"2", "Name in direct order"
		);
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"a", "Corporate name or jurisdiction name as entry element", "NR",
			"b", "Subordinate unit", "R",
			"c", "Location of meeting", "R",
			"d", "Date of meeting or treaty signing", "R",
			"e", "Relator term", "R",
			"f", "Date of a work", "NR",
			"g", "Miscellaneous information", "R",
			"h", "Medium", "NR",
			"k", "Form subheading", "R",
			"l", "Language of a work", "NR",
			"m", "Medium of performance for music", "R",
			"n", "Number of part/section/meeting", "R",
			"o", "Arranged statement for music", "NR",
			"p", "Name of part/section of a work", "R",
			"r", "Key for music", "NR",
			"s", "Version", "NR",
			"t", "Title of a work", "NR",
			"u", "Affiliation", "NR",
			"v", "Volume/sequential designation", "NR",
			"w", "Bibliographic record control number", "R",
			"x", "International Standard Serial Number", "NR",
			"0", "Authority record control number or standard number", "R",
			"3", "Materials specified", "NR",
			"4", "Relationship", "R",
			"5", "Institution to which field applies", "R",
			"6", "Linkage", "NR",
			"7", "Control subfield", "NR",
			"8", "Field link and sequence number", "R"
		);
		// TODO: check these values
		getSubfield("7").setCodes(
			"/0", "Type of record",
			"/1", "Bibliographic level"
		);
	}
}
