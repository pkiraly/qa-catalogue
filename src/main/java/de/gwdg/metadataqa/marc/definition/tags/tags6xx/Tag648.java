package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;

/**
 * Subject Added Entry - Chronological Term
 * http://www.loc.gov/marc/bibliographic/bd648.html,
 */
public class Tag648 extends DataFieldDefinition {

	private static Tag648 uniqueInstance;

	private Tag648() {
		initialize();
		postCreation();
	}

	public static Tag648 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag648();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "648";
		label = "Subject Added Entry - Chronological Term";
		bibframeTag = "Temporal";
		mqTag = "ChronologicalSubject";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator()
			.setHistoricalCodes(
				" ", "No information provided [OBSOLETE, 2014]",
				"0", "Date or time period depicted [OBSOLETE, 2014]",
				"1", "Date or time period of creation or origin [OBSOLETE, 2014]"
			);
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Chronological term", "NR",
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

		getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());

		getSubfield("a").setBibframeTag("Temporal").setMqTag("rdf:value");
		getSubfield("v").setBibframeTag("formGenre").setMqTag("formSubdivision");
		getSubfield("x").setBibframeTag("topic").setMqTag("generalSubdivision");
		getSubfield("y").setBibframeTag("temporal").setMqTag("chronologicalSubdivision");
		getSubfield("z").setBibframeTag("geographic").setMqTag("geographicSubdivision");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("2").setMqTag("source");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
