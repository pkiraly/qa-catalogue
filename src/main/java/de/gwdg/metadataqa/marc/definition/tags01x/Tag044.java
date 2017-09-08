package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountrySourceCodes;

/**
 * Country of Publishing/Producing Entity Code
 * http://www.loc.gov/marc/bibliographic/bd044.html
 */
public class Tag044 extends DataFieldDefinition {

	private static Tag044 uniqueInstance;

	private Tag044(){
		initialize();
	}

	public static Tag044 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag044();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "044";
		label = "Country of Publishing/Producing Entity Code";
		cardinality = Cardinality.Nonrepeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
				"a", "MARC country code", "R",
				"b", "Local subentity code", "R",
				"c", "ISO country code", "R",
				"2", "Source of local subentity code", "R",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
		getSubfield("a").setCodeList(CountryCodes.getInstance());
		getSubfield("2").setCodeList(CountrySourceCodes.getInstance());
	}
}
