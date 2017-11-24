package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.codelist.CartographicDataSourceCodes;

import java.util.Arrays;

/**
 * Coded Cartographic Mathematical Data
 * http://www.loc.gov/marc/bibliographic/bd034.html
 */
public class Tag034 extends DataFieldDefinition {

	private static Tag034 uniqueInstance;

	private Tag034() {
		initialize();
		postCreation();
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
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd034.html";

		ind1 = new Indicator("Type of scale")
			.setCodes(
				"0", "Scale indeterminable/No scale recorded",
				"1", "Single scale",
				"3", "Range of scales"
			)
			.putVersionSpecificCodes(MarcVersion.SZTE, Arrays.asList(
				new Code(" ", "Not specified")
			))
			.setHistoricalCodes(
				"2", "Two or more scales (BK, MP, SE) [OBSOLETE]"
			)
			.setMqTag("typeOfScale");
		ind2 = new Indicator("Type of ring")
			.setCodes(
				" ", "Not applicable",
				"0", "Outer ring",
				"1", "Exclusion ring"
			)
			.setMqTag("typeOfRing");

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

		getSubfield("a").setMqTag("category");
		getSubfield("b").setMqTag("linearHorizontalScale");
		getSubfield("c").setMqTag("linearVerticalScale");
		getSubfield("d").setMqTag("westernmostLongitude");
		getSubfield("e").setMqTag("easternmostLongitude");
		getSubfield("f").setMqTag("northernmostLatitude");
		getSubfield("g").setMqTag("southernmostLatitude");
		getSubfield("h").setMqTag("angularScale");
		getSubfield("j").setMqTag("declinationNorthernLimit");
		getSubfield("k").setMqTag("declinationSouthernLimit");
		getSubfield("m").setMqTag("rightAscensionEasternLimit");
		getSubfield("n").setMqTag("rightAscensionWesternLimit");
		getSubfield("p").setMqTag("equinox");
		getSubfield("r").setMqTag("distanceFromEarth");
		getSubfield("s").setMqTag("gRingLatitude");
		getSubfield("t").setMqTag("gRingLongitude");
		getSubfield("x").setMqTag("beginningDate");
		getSubfield("y").setMqTag("endingDate");
		getSubfield("z").setMqTag("extraterrestrialBody");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("2").setMqTag("source");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
