package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

public class Tag016 extends DataFieldDefinition {

	private static Tag016 uniqueInstance;

	private Tag016(){
		initialize();
	}

	public static Tag016 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag016();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "016";
		label = "National Bibliographic Agency Control Number";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("National bibliographic agency").setCodes(
				" ", "Library and Archives Canada",
				"7", "Source specified in subfield $2"
		);
		ind2 = new Indicator("").setCodes(" ", "Undefined");
		setSubfieldsWithCardinality(
				"a", "Record control number", "NR",
				"z", "Canceled/invalid control number", "R",
				"2", "Source", "NR",
				"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(OrganizationCodes.getInstance());
	}
}
