package de.gwdg.metadataqa.marc.definition.tags80x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;

/**
 * Series Added Entry - Corporate Name
 * http://www.loc.gov/marc/bibliographic/bd810.html
 */
public class Tag810 extends DataFieldDefinition {

	private static Tag810 uniqueInstance;

	private Tag810() {
		initialize();
	}

	public static Tag810 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag810();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "810";
		label = "Series Added Entry - Corporate Name";
		mqTag = "SeriesAddedCorporateName";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator("Type of corporate name entry element").setCodes(
			"0", "Inverted name",
			"1", "Jurisdiction name",
			"2", "Name in direct order"
		).setMqTag("type");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Corporate name or jurisdiction name as entry element", "NR",
			"b", "Subordinate unit", "R",
			"c", "Location of meeting", "R",
			"d", "Date of meeting or treaty signing", "R",
			"e", "Relator term", "R",
			"f", "Date of a work", "NR",
			"g", "Miscellaneous information", "R",
			"h", "Medium", "NR",
			"k", "Form subheading", "R",
			"l", "Language of a work", "NR",
			"m", "Medium of performance for music", "R",
			"n", "Number of part/section/meeting", "R",
			"o", "Arranged statement for music", "NR",
			"p", "Name of part/section of a work", "R",
			"r", "Key for music", "NR",
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
		getSubfield("0").setContentParser(RecordControlNumberParser.getInstance());

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("b").setMqTag("subordinateUnit");
		getSubfield("c").setMqTag("locationOfMeeting");
		getSubfield("d").setMqTag("dates");
		getSubfield("e").setMqTag("relatorTerm");
		getSubfield("f").setMqTag("dateOfAWork");
		getSubfield("g").setMqTag("miscellaneous");
		getSubfield("h").setMqTag("medium");
		getSubfield("k").setMqTag("formSubheading");
		getSubfield("l").setMqTag("language");
		getSubfield("m").setMqTag("mediumOfPerformance");
		getSubfield("n").setMqTag("numberOfPart");
		getSubfield("o").setMqTag("arrangedStatement");
		getSubfield("p").setMqTag("nameOfPart");
		getSubfield("r").setMqTag("keyForMusic");
		getSubfield("s").setMqTag("version");
		getSubfield("t").setMqTag("titleOfAWork");
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
