package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.AuthenticationActionCodes;

/**
 * Authentication Code
 * http://www.loc.gov/marc/bibliographic/bd042.html
 */
public class Tag042 extends DataFieldDefinition {

	private static Tag042 uniqueInstance;

	private Tag042() {
		initialize();
		postCreation();
	}

	public static Tag042 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag042();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "042";
		label = "Authentication Code";
		bibframeTag = "DescriptionAuthentication";
		mqTag = "AuthenticationCode";

		cardinality = Cardinality.Nonrepeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Authentication code", "R"
		);

		getSubfield("a").setCodeList(AuthenticationActionCodes.getInstance());

		getSubfield("a").setMqTag("rdf:value");
	}
}
