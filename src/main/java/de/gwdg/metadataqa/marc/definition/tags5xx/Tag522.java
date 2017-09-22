package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Geographic Coverage Note
 * http://www.loc.gov/marc/bibliographic/bd522.html
 */
public class Tag522 extends DataFieldDefinition {

	private static Tag522 uniqueInstance;

	private Tag522() {
		initialize();
	}

	public static Tag522 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag522();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "522";
		label = "Geographic Coverage Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Display constant controller").setCodes(
			" ", "Geographic coverage",
			"8", "No display constant generated"
		).setMqTag("displayConstant");
		ind2 = new Indicator();
		setSubfieldsWithCardinality(
			"a", "Geographic coverage note", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
	}
}
