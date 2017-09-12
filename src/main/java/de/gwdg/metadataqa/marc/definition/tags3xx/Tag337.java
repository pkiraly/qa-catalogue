package de.gwdg.metadataqa.marc.definition.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.GenreFormCodeAndTermSourceCodes;

/**
 * Media Type
 * http://www.loc.gov/marc/bibliographic/bd337.html
 */
public class Tag337 extends DataFieldDefinition {
	private static Tag337 uniqueInstance;

	private Tag337(){
		initialize();
	}

	public static Tag337 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag337();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "337";
		label = "Media Type";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "Media type term", "R",
				"b", "Media type code", "R",
				"0", "Authority record control number or standard number", "R",
				"2", "Source", "NR",
				"3", "Materials specified", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(GenreFormCodeAndTermSourceCodes.getInstance());
	}
}
