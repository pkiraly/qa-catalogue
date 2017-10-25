package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;

/**
 * Subject Added Entry - Topical Term
 * http://www.loc.gov/marc/bibliographic/bd650.html
 */
public class Tag650 extends DataFieldDefinition {

	private static Tag650 uniqueInstance;

	private Tag650() {
		initialize();
		postCreation();
	}

	public static Tag650 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag650();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "650";
		label = "Subject Added Entry - Topical Term";
		bibframeTag = "Topic";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd650.html";

		ind1 = new Indicator("Level of subject")
			.setCodes(
				" ", "No information provided",
				"0", "No level specified",
				"1", "Primary",
				"2", "Secondary"
			)
			.setMqTag("subjectLevel");
		ind2 = new Indicator("Thesaurus")
			.setCodes(
				"0", "Library of Congress Subject Headings",
				"1", "LC subject headings for children's literature",
				"2", "Medical Subject Headings",
				"3", "National Agricultural Library subject authority file",
				"4", "Source not specified",
				"5", "Canadian Subject Headings",
				"6", "Répertoire de vedettes-matière",
				"7", "Source specified in subfield $2"
			)
			.setMqTag("thesaurus");

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

		getSubfield("a").setMqTag("topicalTerm");
		getSubfield("b").setMqTag("topicalTerm");
		getSubfield("c").setMqTag("locationOfEvent");
		getSubfield("d").setMqTag("activeDates");
		getSubfield("e").setMqTag("relatorTerm");
		getSubfield("g").setMqTag("miscellaneousInformation");
		getSubfield("v").setBibframeTag("formGenre").setMqTag("formSubdivision");
		getSubfield("x").setBibframeTag("topic").setMqTag("generalSubdivision");
		getSubfield("y").setBibframeTag("temporal").setMqTag("chronologicalSubdivision");
		getSubfield("z").setBibframeTag("geographic").setMqTag("geographicSubdivision");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("2").setMqTag("sourceOfHeading");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("4").setMqTag("relationship");
		getSubfield("6").setMqTag("linkage");
		getSubfield("8").setMqTag("fieldLink");

		setHistoricalSubfields(
			"b", "Topical term following geographic name as entry element [OBSOLETE, 1981]"
		);
	}
}
