package de.gwdg.metadataqa.marc.definition.tags80x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Series Added Entry - Personal Name
 * http://www.loc.gov/marc/bibliographic/bd800.html
 */
public class Tag800 extends DataFieldDefinition {

	private static Tag800 uniqueInstance;

	private Tag800() {
		initialize();
	}

	public static Tag800 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag800();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "800";
		label = "Series Added Entry - Personal Name";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Type of personal name entry element").setCodes(
			"0", "Forename",
			"1", "Surname",
			"3", "Family name"
		);
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"a", "Personal name", "NR",
			"b", "Numeration", "NR",
			"c", "Titles and other words associated with a name", "R",
			"d", "Dates associated with a name", "NR",
			"e", "Relator term", "R",
			"f", "Date of a work", "NR",
			"g", "Miscellaneous information", "R",
			"h", "Medium", "NR",
			"j", "Attribution qualifier", "R",
			"k", "Form subheading", "R",
			"l", "Language of a work", "NR",
			"m", "Medium of performance for music", "R",
			"n", "Number of part/section of a work", "R",
			"o", "Arranged statement for music", "NR",
			"p", "Name of part/section of a work", "R",
			"q", "Fuller form of name", "NR",
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
