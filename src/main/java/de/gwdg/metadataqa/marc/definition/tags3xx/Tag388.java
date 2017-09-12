package de.gwdg.metadataqa.marc.definition.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.TemporalTermSourceCodes;

/**
 * Time Period of Creation
 * http://www.loc.gov/marc/bibliographic/bd388.html
 */
public class Tag388 extends DataFieldDefinition {

	private static Tag388 uniqueInstance;

	private Tag388(){
		initialize();
	}

	public static Tag388 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag388();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "388";
		label = "Time Period of Creation";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Type of time period").setCodes(
				" ", "No information provided",
				"1", "Creation of work",
				"2", "Creation of aggregate work"
		);
		ind2 = new Indicator("").setCodes();
		setSubfieldsWithCardinality(
				"a", "Time period of creation term", "R",
				"0", "Authority record control number or standard number", "R",
				"2", "Source", "NR",
				"3", "Materials specified", "NR",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(TemporalTermSourceCodes.getInstance());
	}
}
