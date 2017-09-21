package de.gwdg.metadataqa.marc.definition.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.FunctionTermSourceCodes;

/**
 * Index Term - Function
 * http://www.loc.gov/marc/bibliographic/bd657.html,
 */
public class Tag657 extends DataFieldDefinition {

	private static Tag657 uniqueInstance;

	private Tag657() {
		initialize();
	}

	public static Tag657 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag657();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "657";
		label = "Index Term - Function";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("Source of term").setCodes(
			"7", "Source specified in subfield $2"
		);
		setSubfieldsWithCardinality(
			"a", "Function", "NR",
			"v", "Form subdivision", "R",
			"x", "General subdivision", "R",
			"y", "Chronological subdivision", "R",
			"z", "Geographic subdivision", "R",
			"0", "Authority record control number", "R",
			"2", "Source of term", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(FunctionTermSourceCodes.getInstance());
		getSubfield("v").setBibframeTag("formGenre").setMqTag("formSubdivision");
		getSubfield("x").setBibframeTag("topic").setMqTag("generalSubdivision");
		getSubfield("y").setBibframeTag("temporal").setMqTag("chronologicalSubdivision");
		getSubfield("z").setBibframeTag("geographic").setMqTag("geographicSubdivision");
	}
}
