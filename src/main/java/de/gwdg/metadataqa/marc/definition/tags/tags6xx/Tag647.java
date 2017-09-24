package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Subject Added Entry - Named Event
 * http://www.loc.gov/marc/bibliographic/bd647.html,
 */
public class Tag647 extends DataFieldDefinition {

	private static Tag647 uniqueInstance;

	private Tag647() {
		initialize();
	}

	public static Tag647 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag647();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "647";
		label = "Subject Added Entry - Named Event";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator();
		ind2 = new Indicator("Thesaurus").setCodes(
			"0", "Library of Congress Subject Headings",
			"1", "LC subject headings for children's literature",
			"2", "Medical Subject Headings",
			"3", "National Agricultural Library subject authority file",
			"4", "Source not specified",
			"5", "Canadian Subject Headings",
			"6", "Répertoire de vedettes-matière",
			"7", "Source specified in subfield $2"
		).setMqTag("thesaurus");
		setSubfieldsWithCardinality(
			"a", "Named event", "NR",
			"c", "Location of named event", "R",
			"d", "Date of named event", "NR",
			"g", "Miscellaneous information", "R",
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
		getSubfield("v").setBibframeTag("formGenre").setMqTag("formSubdivision");
		getSubfield("x").setBibframeTag("topic").setMqTag("generalSubdivision");
		getSubfield("y").setBibframeTag("temporal").setMqTag("chronologicalSubdivision");
		getSubfield("z").setBibframeTag("geographic").setMqTag("geographicSubdivision");
	}
}
