package de.gwdg.metadataqa.marc.definition.tags84x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

/**
 * Holding Institution
 * http://www.loc.gov/marc/bibliographic/bd850.html
 */
public class Tag850 extends DataFieldDefinition {

	private static Tag850 uniqueInstance;

	private Tag850() {
		initialize();
	}

	public static Tag850 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag850();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "850";
		label = "Holding Institution";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("");
		ind2 = new Indicator("");
		setSubfieldsWithCardinality(
			"a", "Holding institution", "R",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(OrganizationCodes.getInstance());
	}
}
