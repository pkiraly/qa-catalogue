package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;

/**
 * Subject Added Entry - Geographic Name
 * http://www.loc.gov/marc/bibliographic/bd651.html,
 */
public class Tag651 extends DataFieldDefinition {

	private static Tag651 uniqueInstance;

	private Tag651() {
		initialize();
	}

	public static Tag651 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag651();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "651";
		label = "Subject Added Entry - Geographic Name";
		bibframeTag = "Geographic";
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
			"a", "Geographic name", "NR",
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
		getSubfield("a").setMqTag("rdf:value");
		getSubfield("e").setMqTag("relator");
		getSubfield("g").setMqTag("miscellaneous");
		getSubfield("v").setBibframeTag("formGenre").setMqTag("formSubdivision");
		getSubfield("x").setBibframeTag("topic").setMqTag("generalSubdivision");
		getSubfield("y").setBibframeTag("temporal").setMqTag("chronologicalSubdivision");
		getSubfield("z").setBibframeTag("geographic").setMqTag("geographicSubdivision");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("2").setBibframeTag("source");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("4").setMqTag("relationship");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
