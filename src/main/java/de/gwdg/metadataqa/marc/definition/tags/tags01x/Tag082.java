package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Dewey Decimal Classification Number
 * http://www.loc.gov/marc/bibliographic/bd082.html
 */
public class Tag082 extends DataFieldDefinition {

	private static Tag082 uniqueInstance;

	private Tag082() {
		initialize();
		postCreation();
	}

	public static Tag082 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag082();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "082";
		label = "Dewey Decimal Classification Number";
		bibframeTag = "ClassificationDdc";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd082.html";

		ind1 = new Indicator("Type of edition")
			.setCodes(
				"0", "Full edition",
				"1", "Abridged edition",
				"7", "Other edition specified in subfield $2"
			)
			.setHistoricalCodes(
				" ", "No edition information recorded (BK, MU, VM, SE) [OBSOLETE]",
				"2", "Abridged NST version (BK, MU, VM, SE) [OBSOLETE]"
			)
			.setMqTag("editionType");
		ind2 = new Indicator("Source of classification number")
			.setCodes(
				" ", "No information provided",
				"0", "Assigned by LC",
				"4", "Assigned by agency other than LC"
			)
			.setHistoricalCodes(
				" ", "No information provided [OBSOLETE] [USMARC only, BK, CF, MU, VM, SE]"
			)
			.setMqTag("classificationSource");

		setSubfieldsWithCardinality(
			"a", "Classification number", "R",
			"b", "Item number", "NR",
			"m", "Standard or optional designation", "NR",
			"q", "Assigning agency", "NR",
			"2", "Edition number", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("q").setCodeList(OrganizationCodes.getInstance());

		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setBibframeTag("classificationPortion").setMqTag("rdf:value");
		getSubfield("b").setBibframeTag("itemPortion");
		getSubfield("m").setMqTag("standard");
		getSubfield("q").setBibframeTag("source");
		getSubfield("2").setBibframeTag("edition");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");

		setHistoricalSubfields(
			"b", "DDC number-abridged NST version (SE) [OBSOLETE]"
		);
	}
}
