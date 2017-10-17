package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

/**
 * Record Content Licensor
 * http://www.loc.gov/marc/bibliographic/bd038.html
 */
public class Tag038 extends DataFieldDefinition {

	private static Tag038 uniqueInstance;

	private Tag038() {
		initialize();
		postCreation();
	}

	public static Tag038 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag038();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "038";
		label = "Record Content Licensor";
		bibframeTag = "MetadataLicensor";
		cardinality = Cardinality.Nonrepeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Record content licensor", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("a").setCodeList(OrganizationCodes.getInstance());

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
