package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Copyright Article-Fee Code
 * http://www.loc.gov/marc/bibliographic/bd018.html
 */
public class Tag018 extends DataFieldDefinition {

	private static Tag018 uniqueInstance;

	private Tag018() {
		initialize();
	}

	public static Tag018 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag018();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "018";
		label = "Copyright Article-Fee Code";
		mqTag = "CopyrightArticleFee";
		cardinality = Cardinality.Nonrepeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Copyright article-fee code", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
