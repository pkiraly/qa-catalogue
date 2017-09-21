package de.gwdg.metadataqa.marc.definition.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Subject Added Entry - Chronological Term
 * http://www.loc.gov/marc/bibliographic/bd648.html,
 */
public class Tag648 extends DataFieldDefinition {

	private static Tag648 uniqueInstance;

	private Tag648() {
		initialize();
	}

	public static Tag648 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag648();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "648";
		label = "Subject Added Entry - Chronological Term";
		mqTag = "ChronologicalSubject";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
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
