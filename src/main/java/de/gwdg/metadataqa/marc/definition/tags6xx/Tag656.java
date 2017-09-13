package de.gwdg.metadataqa.marc.definition.tags6xx;

import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.GenreFormCodeAndTermSourceCodes;

/**
 * Index Term - Occupation
 * http://www.loc.gov/marc/bibliographic/bd656.html,
 */
public class Tag656 extends DataFieldDefinition {

	private static Tag656 uniqueInstance;

	private Tag656() {
		initialize();
	}

	public static Tag656 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag656();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "656";
		label = "Index Term - Occupation";
		ind1 = new Indicator("");
		ind2 = new Indicator("Source of term").setCodes(
				"7", "Source specified in subfield $2"
		);
		setSubfieldsWithCardinality(
				"a", "Occupation", "NR",
				"k", "Form", "NR",
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
		// TODO: $2 Occupation Term Source Codes. http://www.loc.gov/standards/sourcelist/occupation.html
		// getSubfield("2").setCodeList(GenreFormCodeAndTermSourceCodes.getInstance());
	}
}
