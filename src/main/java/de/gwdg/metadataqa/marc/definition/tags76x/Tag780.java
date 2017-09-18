package de.gwdg.metadataqa.marc.definition.tags76x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;

/**
 * Preceding Entry
 * http://www.loc.gov/marc/bibliographic/bd780.html
 */
public class Tag780 extends DataFieldDefinition {

	private static Tag780 uniqueInstance;

	private Tag780() {
		initialize();
	}

	public static Tag780 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag780();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "780";
		label = "Preceding Entry";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Note controller").setCodes(
			"0", "Display note",
			"1", "Do not display note"
		);
		ind2 = new Indicator("Type of relationship").setCodes(
			"0", "Continues",
			"1", "Continues in part",
			"2", "Supersedes",
			"3", "Supersedes in part",
			"4", "Formed by the union of ... and ...",
			"5", "Absorbed",
			"6", "Absorbed in part",
			"7", "Separated from"
		);
		setSubfieldsWithCardinality(
			"a", "Main entry heading", "NR",
			"b", "Edition", "NR",
			"c", "Qualifying information", "NR",
			"d", "Place, publisher, and date of publication", "NR",
			"g", "Related parts", "R",
			"h", "Physical description", "NR",
			"i", "Relationship information", "R",
			"k", "Series data for related item", "R",
			"m", "Material-specific details", "NR",
			"n", "Note", "R",
			"o", "Other item identifier", "R",
			"r", "Report number", "R",
			"s", "Uniform title", "NR",
			"t", "Title", "NR",
			"u", "Standard Technical Report Number", "NR",
			"w", "Record control number", "R",
			"x", "International Standard Serial Number", "NR",
			"y", "CODEN designation", "NR",
			"z", "International Standard Book Number", "R",
			"4", "Relationship", "R",
			"6", "Linkage", "NR",
			"7", "Control subfield", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("7").setCodes(
			"0", "Type of main entry heading",
			"1", "Form of name",
			"2", "Type of record",
			"3", "Bibliographic level"
		);
		getSubfield("4").setCodeList(RelatorCodes.getInstance());
	}
}
