package de.gwdg.metadataqa.marc.definition.tags6xx;

import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;

/**
 * Subject Added Entry - Personal Name
 * http://www.loc.gov/marc/bibliographic/bd600.html,
 * http://www.loc.gov/marc/bibliographic/bdx00.html
 */
public class Tag600 extends DataFieldDefinition {

	private static Tag600 uniqueInstance;

	private Tag600() {
		initialize();
	}

	public static Tag600 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag600();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "600";
		label = "Subject Added Entry - Personal Name";
		ind1 = new Indicator("Type of personal name entry element").setCodes(
				"0", "Forename",
				"1", "Surname",
				"3", "Family name"
		);
		ind2 = new Indicator("Thesaurus").setCodes(
				"0", "Library of Congress Subject Headings",
				"1", "LC subject headings for children's literature",
				"2", "Medical Subject Headings",
				"3", "National Agricultural Library subject authority file",
				"4", "Source not specified",
				"5", "Canadian Subject Headings",
				"6", "Répertoire de vedettes-matière",
				"7", "Source specified in subfield $2"
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
				"v", "Form subdivision", "R",
				"x", "General subdivision", "R",
				"y", "Chronological subdivision", "R",
				"z", "Geographic subdivision", "R",
				"0", "Authority record control number or standard number", "R",
				"2", "Source of heading or term", "NR",
				"3", "Materials specified", "NR",
				"4", "Relationship", "R",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());
		getSubfield("4").setCodeList(RelatorCodes.getInstance());
	}
}
