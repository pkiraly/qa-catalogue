package de.gwdg.metadataqa.marc.definition.tags.tags4xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Series Statement/Added Entry-Title
 * http://www.loc.gov/marc/bibliographic/bd440.html
 */
public class Tag440 extends DataFieldDefinition {

	private static Tag440 uniqueInstance;

	private Tag440() {
		initialize();
		postCreation();
	}

	public static Tag440 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag440();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "440";
		label = "Series Statement/Added Entry-Title";
		mqTag = "SeriesStatementAddedEntryTitle";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd440.html";

		ind1 = new Indicator();
		ind2 = new Indicator("Nonfiling characters")
			.setCodes(
				"0", "No nonfiling characters",
				"1-9", "Number of nonfiling characters"
			)
			.setMqTag("nonfilingCharacters");
		ind2.getCode("1-9").setRange(true);

		setSubfieldsWithCardinality(
			"a", "Title", "NR",
			"n", "Number of part/section of a work", "R",
			"p", "Name of part/section of a work", "R",
			"v", "Volume/sequential designation", "NR",
			"w", "Bibliographic record control number", "R",
			"x", "International Standard Serial Number", "NR",
			"0", "Authority record control number", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("n").setMqTag("numberOfPart");
		getSubfield("p").setMqTag("nameOfPart");
		getSubfield("v").setMqTag("volume");
		getSubfield("w").setMqTag("bibliographicRecordControlNumber");
		getSubfield("x").setMqTag("issn");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");

		setHistoricalSubfields(
			"h", "General material designation [OBSOLETE, 1997] [CAN/MARC only]"
		);
	}
}
