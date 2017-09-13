package de.gwdg.metadataqa.marc.definition.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Copy and Version Identification Note
 * http://www.loc.gov/marc/bibliographic/bd562.html
 */
public class Tag562 extends DataFieldDefinition {

	private static Tag562 uniqueInstance;

	private Tag562() {
		initialize();
	}

	public static Tag562 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag562();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "562";
		label = "Copy and Version Identification Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Identifying markings", "R",
				"b", "Copy identification", "R",
				"c", "Version identification", "R",
				"d", "Presentation format", "R",
				"e", "Number of copies", "R",
				"3", "Materials specified", "NR",
				"5", "Institution to which field applies", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
