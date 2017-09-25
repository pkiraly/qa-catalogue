package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * National Agricultural Library Copy Statement
 * http://www.loc.gov/marc/bibliographic/bd071.html
 */
public class Tag071 extends DataFieldDefinition {

	private static Tag071 uniqueInstance;

	private Tag071() {
		initialize();
	}

	public static Tag071 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag071();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "071";
		label = "National Agricultural Library Copy Statement";
		mqTag = "NalCopy";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Classification number", "R",
			"b", "Item number", "NR",
			"c", "Copy information", "R",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("classificationPortion").setMqTag("classification");
		getSubfield("b").setBibframeTag("itemPortion").setMqTag("item");
		getSubfield("0").setMqTag("copy");
		getSubfield("8").setMqTag("fieldLink");
	}
}
