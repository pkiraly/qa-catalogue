package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;

/**
 * Funding Information Note
 * http://www.loc.gov/marc/bibliographic/bd536.html
 */
public class Tag536 extends DataFieldDefinition {

	private static Tag536 uniqueInstance;

	private Tag536() {
		initialize();
	}

	public static Tag536 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag536();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "536";
		bibframeTag = "fundingInformation";
		label = "Funding Information Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Text of note", "NR",
				"b", "Contract number", "R",
				"c", "Grant number", "R",
				"d", "Undifferentiated number", "R",
				"e", "Program element number", "R",
				"f", "Project number", "R",
				"g", "Task number", "R",
				"h", "Work unit number", "R",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
