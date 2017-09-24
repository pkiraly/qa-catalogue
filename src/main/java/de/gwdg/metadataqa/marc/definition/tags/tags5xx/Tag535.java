package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;

/**
 * Location of Originals/Duplicates Note
 * http://www.loc.gov/marc/bibliographic/bd535.html
 */
public class Tag535 extends DataFieldDefinition {

	private static Tag535 uniqueInstance;

	private Tag535() {
		initialize();
	}

	public static Tag535 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag535();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "535";
		label = "Location of Originals/Duplicates Note";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Custodial role").setCodes(
			"1", "Holder of originals",
			"2", "Holder of duplicates"
		);
		ind2 = new Indicator();
		setSubfieldsWithCardinality(
			"a", "Custodian", "NR",
			"b", "Postal address", "R",
			"c", "Country", "R",
			"d", "Telecommunications address", "R",
			"g", "Repository location code", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("g").setCodeList(CountryCodes.getInstance());
	}
}
