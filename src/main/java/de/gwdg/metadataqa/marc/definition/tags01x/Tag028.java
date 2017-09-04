package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag028 extends DataFieldDefinition {

	private static Tag028 uniqueInstance;

	private Tag028() {
		initialize();
	}

	public static Tag028 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag028();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "028";
		label = "Publisher or Distributor Number";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Type of number").setCodes(
				"0", "Issue number",
				"1", "Matrix number",
				"2", "Plate number",
				"3", "Other music publisher number",
				"4", "Video recording publisher number",
				"5", "Other publisher number",
				"6", "Distributor number"
		);
		ind2 = new Indicator("Note/added entry controller").setCodes(
				"0", "No note, no added entry",
				"1", "Note, added entry",
				"2", "Note, no added entry",
				"3", "No note, added entry"
		);
		setSubfieldsWithCardinality(
				"a", "Publisher or distributor number", "NR",
				"b", "Source", "NR",
				"q", "Qualifying information", "R",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
		// getSubfield("5").setCodeList(OrganizationCodes.getInstance());
	}
}
