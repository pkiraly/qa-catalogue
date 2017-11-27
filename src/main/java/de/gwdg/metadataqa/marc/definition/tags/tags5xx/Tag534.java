package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.ISBNValidator;

/**
 * Original Version Note
 * http://www.loc.gov/marc/bibliographic/bd534.html
 */
public class Tag534 extends DataFieldDefinition {

	private static Tag534 uniqueInstance;

	private Tag534() {
		initialize();
		postCreation();
	}

	public static Tag534 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag534();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "534";
		label = "Original Version Note";
		mqTag = "OriginalVersion";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd534.html";

		ind1 = new Indicator()
			.setHistoricalCodes(
				"0", "Note exclude series of original",
				"1", "Note includes series of original"
			);
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Main entry of original", "NR",
			"b", "Edition statement of original", "NR",
			"c", "Publication, distribution, etc. of original", "NR",
			"e", "Physical description, etc. of original", "NR",
			"f", "Series statement of original", "R",
			"k", "Key title of original", "R",
			"l", "Location of original", "NR",
			"m", "Material specific details", "NR",
			"n", "Note about original", "R",
			"o", "Other resource identifier", "R",
			"p", "Introductory phrase", "NR",
			"t", "Title statement of original", "NR",
			"x", "International Standard Serial Number", "R",
			"z", "International Standard Book Number", "R",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("z").setValidator(ISBNValidator.getInstance());

		getSubfield("a").setMqTag("mainEntry");
		getSubfield("b").setMqTag("edition");
		getSubfield("c").setMqTag("publication");
		getSubfield("e").setMqTag("physicalDescription");
		getSubfield("f").setMqTag("series");
		getSubfield("k").setMqTag("keyTitle");
		getSubfield("l").setMqTag("location");
		getSubfield("m").setMqTag("material");
		getSubfield("n").setMqTag("note");
		getSubfield("o").setMqTag("otherIdentifier");
		getSubfield("p").setMqTag("introductoryPhrase");
		getSubfield("t").setMqTag("title");
		getSubfield("x").setMqTag("issn");
		getSubfield("z").setMqTag("isbn");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
