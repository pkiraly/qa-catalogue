package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Study Program Information Note
 * http://www.loc.gov/marc/bibliographic/bd526.html
 */
public class Tag526 extends DataFieldDefinition {

	private static Tag526 uniqueInstance;

	private Tag526() {
		initialize();
		postCreation();
	}

	public static Tag526 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag526();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "526";
		label = "Study Program Information Note";
		mqTag = "StudyProgram";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd526.html";

		ind1 = new Indicator("Display constant controller")
			.setCodes(
				"0", "Reading program",
				"8", "No display constant generated"
			)
			.setMqTag("displayConstant");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Program name", "NR",
			"b", "Interest level", "NR",
			"c", "Reading level", "NR",
			"d", "Title point value", "NR",
			"i", "Display text", "NR",
			"x", "Nonpublic note", "R",
			"z", "Public note", "R",
			"5", "Institution to which field applies", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("5").setCodeList(OrganizationCodes.getInstance());

		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setBibframeTag("studyProgramName");
		getSubfield("b").setBibframeTag("interestLevel");
		getSubfield("c").setBibframeTag("readingLevel");
		getSubfield("d").setBibframeTag("titlePoint");
		getSubfield("i").setBibframeTag("displayText");
		getSubfield("x").setBibframeTag("nonpublicNote");
		getSubfield("z").setBibframeTag("publicNote");
		getSubfield("5").setMqTag("institutionToWhichFieldApplies");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
