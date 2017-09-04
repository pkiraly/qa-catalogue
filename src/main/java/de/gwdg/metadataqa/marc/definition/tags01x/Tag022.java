package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag022 extends DataFieldDefinition {

	private static Tag022 uniqueInstance;

	private Tag022() {
		initialize();
	}

	public static Tag022 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag022();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "022";
		label = "International Standard Serial Number";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("").setCodes(
				" ", "No level specified",
				"0", "Continuing resource of international interest",
				"1", "Continuing resource not of international interest"
		);
		ind2 = new Indicator("").setCodes(" ", "Undefined");
		setSubfieldsWithCardinality(
				"a", "International Standard Serial Number", "NR",
				"l", "ISSN-L", "NR",
				"m", "Canceled ISSN-L", "R",
				"y", "Incorrect ISSN", "R",
				"z", "Canceled ISSN", "R",
				"2", "Source", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
		// TODO check against ISSN National Centres code list http://www.issn.org/
		// getSubfield("2").setCodeList();
	}
}
