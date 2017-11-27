package de.gwdg.metadataqa.marc.definition.tags.tags1xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Main Entry - Uniform Title
 * https://www.loc.gov/marc/bibliographic/bd130.html
 */
public class Tag130 extends DataFieldDefinition {

	private static Tag130 uniqueInstance;

	private Tag130() {
		initialize();
		postCreation();
	}

	public static Tag130 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag130();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "130";
		label = "Main Entry - Uniform Title";
		mqTag = "MainUniformTitle";
		cardinality = Cardinality.Nonrepeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd130.html";

		ind1 = new Indicator("Nonfiling characters")
			.setCodes(
				"0-9", "Number of nonfiling characters"
			)
			.setMqTag("nonfilingCharacters");
		ind1.getCode("0-9").setRange(true);
		ind2 = new Indicator();

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
			"0", "Authority record control number or standard number", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("6").setContentParser(LinkageParser.getInstance());

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
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("6").setMqTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
