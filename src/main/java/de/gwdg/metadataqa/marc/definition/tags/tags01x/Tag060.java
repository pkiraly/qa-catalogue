package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * National Library of Medicine Call Number
 * http://www.loc.gov/marc/bibliographic/bd060.html
 */
public class Tag060 extends DataFieldDefinition {

	private static Tag060 uniqueInstance;

	private Tag060() {
		initialize();
		postCreation();
	}

	public static Tag060 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag060();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "060";
		label = "National Library of Medicine Call Number";
		bibframeTag = "ClassificationNlm";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator("Existence in NLM collection")
			.setCodes(
				" ", "No information provided",
				"0", "Item is in NLM",
				"1", "Item is not in NLM"
			)
			.setMqTag("existenceInNLM");
		ind2 = new Indicator("Source of call number")
			.setCodes(
				"0", "Assigned by NLM",
				"4", "Assigned by agency other than NLM"
			)
			.setHistoricalCodes(
				"0", "No series involved",
				"1", "Main series",
				"2", "Subseries",
				"3", "Sub-subseries",
				" ", "No information provided [OBSOLETE]"
			)
			.setMqTag("source");

		setSubfieldsWithCardinality(
			"a", "Classification number", "R",
			"b", "Item number", "NR",
			"0", "Authority record control number or standard number", "R",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("classificationPortion");
		getSubfield("b").setBibframeTag("itemPortion");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("8").setMqTag("fieldLink");
	}
}
