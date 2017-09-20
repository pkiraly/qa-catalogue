package de.gwdg.metadataqa.marc.definition.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.GenreFormCodeAndTermSourceCodes;

/**
 * Index Term - Genre/Form
 * http://www.loc.gov/marc/bibliographic/bd655.html,
 */
public class Tag655 extends DataFieldDefinition {

	private static Tag655 uniqueInstance;

	private Tag655() {
		initialize();
	}

	public static Tag655 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag655();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "655";
		label = "Index Term - Genre/Form";
		bibframeTag = "GenreForm";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Type of heading").setCodes(
			" ", "Basic",
			"0", "Faceted"
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
			"a", "Genre/form data or focus term", "NR",
			"b", "Non-focus term", "R",
			"c", "Facet/hierarchy designation", "R",
			"v", "Form subdivision", "R",
			"x", "General subdivision", "R",
			"y", "Chronological subdivision", "R",
			"z", "Geographic subdivision", "R",
			"0", "Authority record control number", "R",
			"2", "Source of term", "NR",
			"3", "Materials specified", "NR",
			"5", "Institution to which field applies", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(GenreFormCodeAndTermSourceCodes.getInstance());
		getSubfield("a").setBibframeTag("rdfs:label");
		getSubfield("b").setMqTag("nonfocusTerm");
		getSubfield("c").setMqTag("facet");
		getSubfield("v").setBibframeTag("genreForm").setMqTag("formSubdivision");
		getSubfield("x").setBibframeTag("topic").setMqTag("generalSubdivision");
		getSubfield("y").setBibframeTag("temporal").setMqTag("chronologicalSubdivision");
		getSubfield("z").setBibframeTag("geographic").setMqTag("geographicSubdivision");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("5").setMqTag("institutionToWhichFieldApplies");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
