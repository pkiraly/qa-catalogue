package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * GPO Item Number
 * http://www.loc.gov/marc/bibliographic/bd074.html
 */
public class Tag074 extends DataFieldDefinition {

	private static Tag074 uniqueInstance;

	private Tag074() {
		initialize();
	}

	public static Tag074 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag074();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "074";
		label = "GPO Item Number";
		mqTag = "GPOItemNumber";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "GPO item number", "NR",
			"z", "Canceled/invalid GPO item number", "R",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setBibframeTag("rdf:value");
		getSubfield("z").setMqTag("canceled");
		getSubfield("8").setMqTag("fieldLink");
	}
}
