package de.gwdg.metadataqa.marc.definition.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;

/**
 * Subject Added Entry - Topical Term
 * http://www.loc.gov/marc/bibliographic/bd650.html,
 */
public class Tag650 extends DataFieldDefinition {

	private static Tag650 uniqueInstance;

	private Tag650() {
		initialize();
	}

	public static Tag650 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag650();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "650";
		label = "Subject Added Entry - Topical Term";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Level of subject").setCodes(
			" ", "No information provided",
			"0", "No level specified",
			"1", "Primary",
			"2", "Secondary"
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
			"a", "Topical term or geographic name entry element", "NR",
			"b", "Topical term following geographic name entry element", "NR",
			"c", "Location of event", "NR",
			"d", "Active dates", "NR",
			"e", "Relator term", "R",
			"g", "Miscellaneous information", "R",
			"4", "Relationship", "R",
			"v", "Form subdivision", "R",
			"x", "General subdivision", "R",
			"y", "Chronological subdivision", "R",
			"z", "Geographic subdivision", "R",
			"0", "Authority record control number or standard number", "R",
			"2", "Source of heading or term", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("4").setCodeList(RelatorCodes.getInstance());
		getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());
	}
}
