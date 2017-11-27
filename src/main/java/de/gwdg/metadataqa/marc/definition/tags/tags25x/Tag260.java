package de.gwdg.metadataqa.marc.definition.tags.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Publication, Distribution, etc. (Imprint)
 * http://www.loc.gov/marc/bibliographic/bd260.html
 */
public class Tag260 extends DataFieldDefinition {

	private static Tag260 uniqueInstance;

	private Tag260() {
		initialize();
		postCreation();
	}

	public static Tag260 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag260();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "260";
		label = "Publication, Distribution, etc. (Imprint)";
		bibframeTag = "Publication";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd260.html";

		ind1 = new Indicator("Sequence of publishing statements")
			.setCodes(
				" ", "Not applicable/No information provided/Earliest available publisher",
				"2", "Intervening publisher",
				"3", "Current/latest publisher"
			)
			.setHistoricalCodes(
				"0", "Publisher, distributor, etc. is present",
				"1", "Publisher, distributor, etc. not present"
			)
			.setMqTag("sequenceOfPublishingStatements");
		ind2 = new Indicator()
			.setHistoricalCodes(
				"0", "Publisher, distributor, etc. not same as issuing body in added entry",
				"1", "Publisher, distributor, etc. same as issuing body in added entry"
			);

		setSubfieldsWithCardinality(
			"a", "Place of publication, distribution, etc.", "R",
			"b", "Name of publisher, distributor, etc.", "R",
			"c", "Date of publication, distribution, etc.", "R",
			"e", "Place of manufacture", "R",
			"f", "Manufacturer", "R",
			"g", "Date of manufacture", "R",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setBibframeTag("place");
		getSubfield("b").setBibframeTag("agent");
		getSubfield("c").setBibframeTag("date");
		getSubfield("e").setMqTag("placeOfManufacture");
		getSubfield("f").setMqTag("manufacturer");
		getSubfield("g").setMqTag("dateOfManufacture");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setMqTag("linkage");
		getSubfield("8").setMqTag("fieldLink");

		setHistoricalSubfields(
			"d", "Plate or publisher's number for music (Pre-AACR2) [OBSOLETE, 1981] [CAN/MARC only] / Plate or publisher's number for music (Pre-AACR2) [OBSOLETE, 1999] [USMARC only]",
			"k", "Identification/manufacturer number [OBSOLETE, 1988] [CAN/MARC only]",
			"l", "Matrix and/or take number [OBSOLETE, 1988] [CAN/MARC only]"
		);
	}
}
