package de.gwdg.metadataqa.marc.definition.tags80x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;

/**
 * Series Added Entry - Meeting Name
 * http://www.loc.gov/marc/bibliographic/bd811.html
 */
public class Tag811 extends DataFieldDefinition {

	private static Tag811 uniqueInstance;

	private Tag811() {
		initialize();
	}

	public static Tag811 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag811();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "811";
		label = "Series Added Entry - Meeting Name";
		mqTag = "SeriesAddedMeetingName";
		cardinality = Cardinality.Repeatable;
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
			"h", "Medium", "NR",
			"j", "Relator term", "R",
			"k", "Form subheading", "R",
			"l", "Language of a work", "NR",
			"n", "Number of part/section/meeting", "R",
			"p", "Name of part/section of a work", "R",
			"q", "Name of meeting following jurisdiction name entry element", "NR",
			"s", "Version", "NR",
			"t", "Title of a work", "NR",
			"u", "Affiliation", "NR",
			"v", "Volume/sequential designation", "NR",
			"w", "Bibliographic record control number", "R",
			"x", "International Standard Serial Number", "NR",
			"0", "Authority record control number or standard number", "R",
			"3", "Materials specified", "NR",
			"4", "Relationship", "R",
			"5", "Institution to which field applies", "R",
			"6", "Linkage", "NR",
			"7", "Control subfield", "NR",
			"8", "Field link and sequence number", "R"
		);
		// TODO: check these values
		getSubfield("7").setCodes(
			"/0", "Type of record",
			"/1", "Bibliographic level"
		);
		getSubfield("4").setCodeList(RelatorCodes.getInstance());
		getSubfield("a").setMqTag("rdf:value");
		getSubfield("c").setMqTag("locationOfMeeting");
		getSubfield("d").setMqTag("dates");
		getSubfield("e").setMqTag("subordinateUnit");
		getSubfield("f").setMqTag("dateOfAWork");
		getSubfield("g").setMqTag("miscellaneous");
		getSubfield("h").setMqTag("medium");
		getSubfield("j").setMqTag("relatorTerm");
		getSubfield("k").setMqTag("formSubheading");
		getSubfield("l").setMqTag("language");
		getSubfield("n").setMqTag("numberOfPart");
		getSubfield("p").setMqTag("nameOfPart");
		getSubfield("q").setMqTag("followingName");
		getSubfield("s").setMqTag("version");
		getSubfield("t").setMqTag("titleOfAWork");
		getSubfield("u").setMqTag("affiliation");
		getSubfield("u").setMqTag("affiliation");
		getSubfield("v").setMqTag("volumeDesignation");
		getSubfield("w").setMqTag("bibliographicRecordControlNumber");
		getSubfield("x").setMqTag("issn");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("4").setMqTag("relatorCode");
		getSubfield("5").setMqTag("institutionToWhichFieldApplies");
		getSubfield("6").setMqTag("linkage");
		getSubfield("7").setMqTag("controlSubfield");
		getSubfield("8").setMqTag("fieldLink");
	}
}
