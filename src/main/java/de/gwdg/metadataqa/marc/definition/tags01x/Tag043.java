package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.GeographicAreaSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.GeographicAreaCodes;

/**
 * Geographic Area Code
 * http://www.loc.gov/marc/bibliographic/bd043.html
 */
public class Tag043 extends DataFieldDefinition {

	private static Tag043 uniqueInstance;

	private Tag043() {
		initialize();
	}

	public static Tag043 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag043();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "043";
		label = "Geographic Area Code";
		cardinality = Cardinality.Nonrepeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"a", "Geographic area code", "R",
			"b", "Local GAC code", "R",
			"c", "ISO code", "R",
			"0", "Authority record control number or standard number", "R",
			"2", "Source of local code", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("a").setCodeList(GeographicAreaCodes.getInstance());
		getSubfield("2").setCodeList(GeographicAreaSourceCodes.getInstance());
	}
}
