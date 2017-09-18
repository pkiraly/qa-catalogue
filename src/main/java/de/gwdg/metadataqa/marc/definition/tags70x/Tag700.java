package de.gwdg.metadataqa.marc.definition.tags70x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;

/**
 * Added Entry - Personal Name
 * http://www.loc.gov/marc/bibliographic/bd700.html
 */
public class Tag700 extends DataFieldDefinition {

	private static Tag700 uniqueInstance;

	private Tag700() {
		initialize();
	}

	public static Tag700 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag700();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "700";
		label = "Added Entry - Personal Name";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Type of personal name entry element").setCodes(
			"0", "Forename",
			"1", "Surname",
			"3", "Family name"
		);
		ind2 = new Indicator("Type of added entry").setCodes(
			" ", "No information provided",
			"2", "Analytical entry"
		);
		setSubfieldsWithCardinality(
			"a", "Personal name", "NR",
			"b", "Numeration", "NR",
			"c", "Titles and other words associated with a name", "R",
			"d", "Dates associated with a name", "NR",
			"e", "Relator term", "R",
			"f", "Date of a work", "NR",
			"g", "Miscellaneous information", "R",
			"h", "Medium", "NR",
			"i", "Relationship information", "R",
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
			"x", "International Standard Serial Number", "NR",
			"0", "Authority record control number or standard number", "R",
			"3", "Materials specified", "NR",
			"4", "Relationship", "R",
			"5", "Institution to which field applies", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("4").setCodeList(RelatorCodes.getInstance());
	}
}
