package de.gwdg.metadataqa.marc.definition.tags.tags84x;

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
		postCreation();
	}

	public static Tag850 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag850();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "850";
		label = "Holding Institution";
		bibframeTag = "HeldBy";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Holding institution", "R",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setCodeList(OrganizationCodes.getInstance());

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("8").setMqTag("fieldLink");

		setHistoricalSubfields(
			"b", "Holdings (MU, VM, SE) [OBSOLETE, 1990]",
			"d", "Inclusive dates (MU, VM, SE) [OBSOLETE, 1990]",
			"e", "Retention statement (CF, MU, VM, SE) [OBSOLETE, 1990]"
		);
	}
}
