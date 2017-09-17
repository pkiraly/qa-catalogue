package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Publications About Described Materials Note
 * http://www.loc.gov/marc/bibliographic/bd581.html
 */
public class Tag581 extends DataFieldDefinition {

	private static Tag581 uniqueInstance;

	private Tag581() {
		initialize();
	}

	public static Tag581 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag581();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "581";
		label = "Publications About Described Materials Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Display constant controller").setCodes(
			" ", "Publications",
			"8", "No display constant generated"
		);
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"a", "Publications about described materials note", "NR",
			"z", "International Standard Book Number", "R",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
	}
}
