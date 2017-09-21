package de.gwdg.metadataqa.marc.definition.tags1xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;

/**
 * Main Entry - Meeting Name
 * https://www.loc.gov/marc/bibliographic/bd111.html
 */
public class Tag111 extends DataFieldDefinition {

	private static Tag111 uniqueInstance;

	private Tag111() {
		initialize();
	}

	public static Tag111 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag111();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "111";
		label = "Main Entry - Meeting Name";
		mqTag = "MainMeetingName";
		cardinality = Cardinality.Nonrepeatable;
		ind1 = new Indicator("Type of meeting name entry element").setCodes(
			"0", "Inverted name",
			"1", "Jurisdiction name",
			"2", "Name in direct order"
		);
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"a", "Meeting name or jurisdiction name as entry element", "NR",
			"c", "Location of meeting", "R",
			"d", "Date of meeting", "NR",
			"e", "Subordinate unit", "R",
			"f", "Date of a work", "NR",
			"g", "Miscellaneous information", "R",
			"j", "Relator term", "R",
			"k", "Form subheading", "R",
			"l", "Language of a work", "NR",
			"n", "Number of part/section/meeting", "R",
			"p", "Name of part/section of a work", "R",
			"q", "Name of meeting following jurisdiction name entry element", "NR",
			"t", "Title of a work", "NR",
			"u", "Affiliation", "NR",
			"0", "Authority record control number or standard number", "R",
			"4", "Relationship", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("4").setCodeList(RelatorCodes.getInstance());
		getSubfield("a").setMqTag("rdf:value");
		getSubfield("c").setMqTag("locationOfMeeting");
		getSubfield("d").setMqTag("dates");
		getSubfield("e").setMqTag("subordinateUnit");
		getSubfield("f").setMqTag("dateOfAWork");
		getSubfield("g").setMqTag("miscellaneous");
		getSubfield("j").setMqTag("relatorTerm");
		getSubfield("k").setMqTag("formSubheading");
		getSubfield("l").setMqTag("language");
		getSubfield("n").setMqTag("numberOfPart");
		getSubfield("p").setMqTag("nameOfPart");
		getSubfield("q").setMqTag("followingName");
		getSubfield("t").setMqTag("titleOfAWork");
		getSubfield("u").setMqTag("affiliation");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("4").setMqTag("relatorCode");
		getSubfield("6").setMqTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
