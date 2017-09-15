package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

/**
 * Synthesized Classification Number Components
 * http://www.loc.gov/marc/bibliographic/bd085.html
 */
public class Tag085 extends DataFieldDefinition {

	private static Tag085 uniqueInstance;

	private Tag085() {
		initialize();
	}

	public static Tag085 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag085();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "085";
		label = "Synthesized Classification Number Components";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Number where instructions are found-single number or beginning number of span", "R",
				"b", "Base number", "R",
				"c", "Classification number-ending number of span", "R",
				"f", "Facet designator", "R",
				"r", "Root number", "R",
				"s", "Digits added from classification number in schedule or external table", "R",
				"t", "Digits added from internal subarrangement or add table", "R",
				"u", "Number being analyzed", "R",
				"v", "Number in internal subarrangement or add table where instructions are found", "R",
				"w", "Table identification-Internal subarrangement or add table", "R",
				"y", "Table sequence number for internal subarrangement or add table", "R",
				"z", "Table identification", "R",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
	}
}
