package de.gwdg.metadataqa.marc.definition.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.ThematicIndexCodeSourceCodes;

/**
 * Numeric Designation of Musical Work
 * http://www.loc.gov/marc/bibliographic/bd383.html
 */
public class Tag383 extends DataFieldDefinition {
	private static Tag383 uniqueInstance;

	private Tag383() {
		initialize();
	}

	public static Tag383 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag383();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "383";
		label = "Numeric Designation of Musical Work";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"a", "Serial number", "R",
			"b", "Opus number", "R",
			"c", "Thematic index number", "R",
			"d", "Thematic index code", "NR",
			"e", "Publisher associated with opus number", "NR",
			"2", "Source", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(ThematicIndexCodeSourceCodes.getInstance());
	}
}
