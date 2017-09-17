package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag020 extends DataFieldDefinition {

	private static Tag020 uniqueInstance;

	private Tag020() {
		initialize();
	}

	public static Tag020 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag020();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "020";
		label = "International Standard Book Number";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("").setCodes(" ", "Undefined");
		ind2 = new Indicator("").setCodes(" ", "Undefined");
		setSubfieldsWithCardinality(
			"a", "International Standard Book Number", "NR",
			"c", "Terms of availability", "NR",
			"q", "Qualifying information", "R",
			"z", "Canceled/invalid ISBN", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		// TODO validation ISO 2108
		// getSubfield("2").setValidator(new ISBNValidator())
		// getSubfield("2").setCodeList(OrganizationCodes.getInstance());
	}
}
