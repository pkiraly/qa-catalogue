package de.gwdg.metadataqa.marc.definition.tags.tags80x;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISSNValidator;

import java.util.Arrays;

/**
 * Series Added Entry - Uniform Title
 * http://www.loc.gov/marc/bibliographic/bd830.html
 */
public class Tag830 extends DataFieldDefinition {

	private static Tag830 uniqueInstance;

	private Tag830() {
		initialize();
		postCreation();
	}

	public static Tag830 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag830();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "830";
		label = "Series Added Entry - Uniform Title";
		mqTag = "SeriesAddedUniformTitle";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd830.html";

		ind1 = new Indicator();
		ind2 = new Indicator("Nonfiling characters")
			.setCodes(
				"0", "No nonfiling characters",
				"1-9", "Number of nonfiling characters"
			)
			.setMqTag("nonfilingCharacters");
		ind2.getCode("1-9").setRange(true);

		setSubfieldsWithCardinality(
			"a", "Uniform title", "NR",
			"d", "Date of treaty signing", "R",
			"f", "Date of a work", "NR",
			"g", "Miscellaneous information", "R",
			"h", "Medium", "NR",
			"k", "Form subheading", "R",
			"l", "Language of a work", "NR",
			"m", "Medium of performance for music", "R",
			"n", "Number of part/section of a work", "R",
			"o", "Arranged statement for music", "NR",
			"p", "Name of part/section of a work", "R",
			"r", "Key for music", "NR",
			"s", "Version", "NR",
			"t", "Title of a work", "NR",
			"v", "Volume/sequential designation", "NR",
			"w", "Bibliographic record control number", "R",
			"x", "International Standard Serial Number", "NR",
			"0", "Authority record control number or standard number", "R",
			"3", "Materials specified", "NR",
			"5", "Institution to which field applies", "R",
			"6", "Linkage", "NR",
			"7", "Control subfield", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("7").setPositions(Arrays.asList(
			new ControlSubfieldDefinition("Type of record", 0, 1)
				.setCodes(Utils.generateCodes(
					"a", "Language material",
					"c", "Notated music",
					"d", "Manuscript notated music",
					"e", "Cartographic material",
					"f", "Manuscript cartographic material",
					"g", "Projected medium",
					"i", "Nonmusical sound recording",
					"j", "Musical sound recording",
					"k", "Two-dimensional nonprojectable graphic",
					"m", "Computer file",
					"o", "Kit",
					"p", "Mixed material",
					"r", "Three-dimensional artifact or naturally occurring object",
					"t", "Manuscript language material"
				)),
			new ControlSubfieldDefinition("Bibliographic level", 1, 2)
				.setCodes(Utils.generateCodes(
					"a", "Monographic component part",
					"b", "Serial component part",
					"c", "Collection",
					"d", "Subunit",
					"i", "Integrating resource",
					"m", "Monograph/item",
					"s", "Serial"
				))
		));

		getSubfield("6").setContentParser(LinkageParser.getInstance());
		getSubfield("x").setValidator(ISSNValidator.getInstance());

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("d").setMqTag("dateOfTreaty");
		getSubfield("f").setMqTag("dateOfAWork");
		getSubfield("g").setMqTag("miscellaneous");
		getSubfield("h").setMqTag("medium");
		getSubfield("k").setMqTag("formSubheading");
		getSubfield("l").setMqTag("languageOfAWork");
		getSubfield("m").setMqTag("mediumOfPerformance");
		getSubfield("n").setMqTag("numberOfPart");
		getSubfield("o").setMqTag("arrangedStatement");
		getSubfield("p").setMqTag("nameOfPart");
		getSubfield("r").setMqTag("keyForMusic");
		getSubfield("s").setMqTag("version");
		getSubfield("t").setMqTag("titleOfAWork");
		getSubfield("v").setMqTag("volume");
		getSubfield("w").setMqTag("biblControlNumber");
		getSubfield("x").setMqTag("issn");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("5").setMqTag("institutionToWhichFieldApplies");
		getSubfield("6").setMqTag("linkage");
		getSubfield("7").setMqTag("controlSubfield");
		getSubfield("8").setMqTag("fieldLink");
	}
}
