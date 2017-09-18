package de.gwdg.metadataqa.marc.definition.tags70x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.NameAndTitleAuthoritySourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;

/**
 * Added Entry - Geographic Name
 * http://www.loc.gov/marc/bibliographic/bd751.html
 */
public class Tag751 extends DataFieldDefinition {

	private static Tag751 uniqueInstance;

	private Tag751() {
		initialize();
	}

	public static Tag751 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag751();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "751";
		label = "Added Entry - Geographic Name";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"a", "Geographic name", "NR",
			"e", "Relator term", "R",
			"0", "Authority record control number or standard number", "R",
			"2", "Source of heading or term", "NR",
			"3", "Materials specified", "NR",
			"4", "Relationship", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(NameAndTitleAuthoritySourceCodes.getInstance());
		getSubfield("4").setCodeList(RelatorCodes.getInstance());
	}
}
