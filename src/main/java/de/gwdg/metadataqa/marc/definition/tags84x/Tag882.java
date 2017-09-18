package de.gwdg.metadataqa.marc.definition.tags84x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

/**
 * Replacement Record Information
 * http://www.loc.gov/marc/bibliographic/bd882.html
 */
public class Tag882 extends DataFieldDefinition {

	private static Tag882 uniqueInstance;

	private Tag882() {
		initialize();
	}

	public static Tag882 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag882();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "882";
		label = "Replacement Record Information";
		cardinality = Cardinality.Nonrepeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"a", "Replacement title", "R",
			"i", "Explanatory text", "R",
			"w", "Replacement bibliographic record control number", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("w").setCodeList(OrganizationCodes.getInstance());
	}
}
