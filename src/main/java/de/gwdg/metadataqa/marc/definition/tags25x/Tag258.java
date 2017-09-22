package de.gwdg.metadataqa.marc.definition.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Philatelic Issue Data
 * http://www.loc.gov/marc/bibliographic/bd258.html
 */
public class Tag258 extends DataFieldDefinition {
	private static Tag258 uniqueInstance;

	private Tag258() {
		initialize();
	}

	public static Tag258 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag258();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "258";
		label = "Philatelic Issue Data";
		mqTag = "PhilatelicIssue";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator();
		ind2 = new Indicator();
		setSubfieldsWithCardinality(
			"a", "Issuing jurisdiction", "NR",
			"b", "Denomination", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("a").setMqTag("jurisdiction");
		getSubfield("b").setMqTag("denomination");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
