package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.CartographicDataSourceCodes;

/**
 * Musical Incipits Information
 * http://www.loc.gov/marc/bibliographic/bd031.html
 */
public class Tag034 extends DataFieldDefinition {

	private static Tag034 uniqueInstance;

	private Tag034() {
		initialize();
	}

	public static Tag034 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag034();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "034";
		label = "Coded Cartographic Mathematical Data";
		bibframeTag = "Scale";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Type of scale").setCodes(
			"0", "Scale indeterminable/No scale recorded",
			"1", "Single scale",
			"3", "Range of scales"
		);
		ind2 = new Indicator("Type of ring").setCodes(
			" ", "Not applicable",
			"0", "Outer ring",
			"1", "Exclusion ring"
		);
		setSubfieldsWithCardinality(
			"a", "Category of scale", "NR",
			"b", "Constant ratio linear horizontal scale", "R",
			"c", "Constant ratio linear vertical scale", "R",
			"d", "Coordinates - westernmost longitude", "NR",
			"e", "Coordinates - easternmost longitude", "NR",
			"f", "Coordinates - northernmost latitude", "NR",
			"g", "Coordinates - southernmost latitude", "NR",
			"h", "Angular scale", "R",
			"j", "Declination - northern limit", "NR",
			"k", "Declination - southern limit", "NR",
			"m", "Right ascension - eastern limit", "NR",
			"n", "Right ascension - western limit", "NR",
			"p", "Equinox", "NR",
			"r", "Distance from earth", "NR",
			"s", "G-ring latitude", "R",
			"t", "G-ring longitude", "R",
			"x", "Beginning date", "NR",
			"y", "Ending date", "NR",
			"z", "Name of extraterrestrial body", "NR",
			"0", "Authority record control number or standard number", "R",
			"2", "Source", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("a").setCodes(
			"a", "Linear scale",
			"b", "Angular scale",
			"z", "Other type of scale"
		);
		getSubfield("2").setCodeList(CartographicDataSourceCodes.getInstance());
	}
}
