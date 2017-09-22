package de.gwdg.metadataqa.marc.definition.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Dates of Publication and/or Sequential Designation
 * http://www.loc.gov/marc/bibliographic/bd362.html
 */
public class Tag362 extends DataFieldDefinition {
	private static Tag362 uniqueInstance;

	private Tag362() {
		initialize();
	}

	public static Tag362 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag362();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "362";
		label = "Dates of Publication and/or Sequential Designation";
		mqTag = "DatesOfPublication";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator("Format of date").setCodes(
			"0", "Formatted style",
			"1", "Unformatted note"
		).setMqTag("format");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Dates of publication and/or sequential designation", "NR",
			"z", "Source of information", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("z").setMqTag("source");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
