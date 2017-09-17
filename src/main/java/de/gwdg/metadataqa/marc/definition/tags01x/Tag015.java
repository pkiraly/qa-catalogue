package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag015 extends DataFieldDefinition {

	private static Tag015 uniqueInstance;

	private Tag015() {
		initialize();
	}

	public static Tag015 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag015();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "015";
		label = "National Bibliography Number";
		cardinality = Cardinality.Repeatable;
		bibframeTag = "identifiedBy/Nbn";
		ind1 = new Indicator("").setCodes(" ", "Undefined");
		ind2 = new Indicator("").setCodes(" ", "Undefined");
		setSubfieldsWithCardinality(
			"a", "National bibliography number", "R",
			"q", "Qualifying information", "R",
			"z", "Canceled/invalid national bibliography number", "R",
			"2", "Source", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("a").setBibframeTag("rdf:value");
		getSubfield("q").setBibframeTag("qualifier");
		getSubfield("2").setBibframeTag("source");
	}
}
