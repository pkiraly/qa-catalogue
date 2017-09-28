package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * National Agricultural Library Call Number
 * http://www.loc.gov/marc/bibliographic/bd070.html
 */
public class Tag070 extends DataFieldDefinition {

	private static Tag070 uniqueInstance;

	private Tag070() {
		initialize();
	}

	public static Tag070 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag070();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "070";
		label = "National Agricultural Library Call Number";
		bibframeTag = "Classification";
		mqTag = "NalCallNumber";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator(" collection").setCodes(
			"0", "Item is in NAL",
			"1", "Item is not in NAL"
		).setMqTag("existenceInNAL");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Classification number", "R",
			"b", "Item number", "NR",
			"0", "Authority record control number or standard number", "R",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("classificationPortion").setMqTag("classification");
		getSubfield("b").setBibframeTag("itemPortion").setMqTag("item");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("8").setMqTag("fieldLink");
	}
}
