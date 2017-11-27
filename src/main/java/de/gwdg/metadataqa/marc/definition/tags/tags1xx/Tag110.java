package de.gwdg.metadataqa.marc.definition.tags.tags1xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Main Entry - Corporate Name
 * https://www.loc.gov/marc/bibliographic/bd110.html
 */
public class Tag110 extends DataFieldDefinition {

	private static Tag110 uniqueInstance;

	private Tag110() {
		initialize();
		postCreation();
	}

	public static Tag110 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag110();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "110";
		label = "Main Entry - Corporate Name";
		mqTag = "MainCorporateName";
		cardinality = Cardinality.Nonrepeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd110.html";

		ind1 = new Indicator("Type of corporate name entry element")
			.setCodes(
				"0", "Inverted name",
				"1", "Jurisdiction name",
				"2", "Name in direct order"
			)
			.setMqTag("type");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Corporate name or jurisdiction name as entry element", "NR",
			"b", "Subordinate unit", "R",
			"c", "Location of meeting", "R",
			"d", "Date of meeting or treaty signing", "R",
			"e", "Relator term", "R",
			"f", "Date of a work", "NR",
			"g", "Miscellaneous information", "R",
			"k", "Form subheading", "R",
			"l", "Language of a work", "NR",
			"n", "Number of part/section/meeting", "R",
			"p", "Name of part/section of a work", "R",
			"t", "Title of a work", "NR",
			"u", "Affiliation", "NR",
			"0", "Authority record control number or standard number", "R",
			"4", "Relator code", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("4").setCodeList(RelatorCodes.getInstance());

		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("b").setMqTag("subordinateUnit");
		getSubfield("c").setMqTag("locationOfMeeting");
		getSubfield("d").setMqTag("dates");
		getSubfield("e").setMqTag("relatorTerm");
		getSubfield("f").setMqTag("dateOfAWork");
		getSubfield("g").setMqTag("miscellaneous");
		getSubfield("k").setMqTag("formSubheading");
		getSubfield("l").setMqTag("language");
		getSubfield("n").setMqTag("numberOfPart");
		getSubfield("p").setMqTag("nameOfPart");
		getSubfield("t").setMqTag("titleOfAWork");
		getSubfield("u").setMqTag("affiliation");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("4").setMqTag("relatorCode");
		getSubfield("6").setMqTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
