package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag027 extends DataFieldDefinition {

	private static Tag027 uniqueInstance;

	private Tag027() {
		initialize();
	}

	public static Tag027 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag027();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "027";
		label = "Standard Technical Report Number";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("").setCodes(" ", "Undefined");
		ind2 = new Indicator("").setCodes(" ", "Undefined");
		setSubfieldsWithCardinality(
				"a", "Standard technical report number", "NR",
				"q", "Qualifying information", "R",
				"z", "Canceled/invalid number", "R",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
		// getSubfield("5").setCodeList(OrganizationCodes.getInstance());
	}
}
