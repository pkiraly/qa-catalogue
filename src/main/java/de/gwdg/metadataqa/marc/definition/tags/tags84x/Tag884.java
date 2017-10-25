package de.gwdg.metadataqa.marc.definition.tags.tags84x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

/**
 * Description Conversion Information
 * http://www.loc.gov/marc/bibliographic/bd884.html
 */
public class Tag884 extends DataFieldDefinition {

	private static Tag884 uniqueInstance;

	private Tag884() {
		initialize();
		postCreation();
	}

	public static Tag884 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag884();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "884";
		label = "Description Conversion Information";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd884.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Conversion process", "NR",
			"g", "Conversion date", "NR",
			"k", "Identifier of source metadata", "NR",
			"q", "Conversion agency", "NR",
			"u", "Uniform Resource Identifier", "R"
		);

		getSubfield("q").setCodeList(OrganizationCodes.getInstance());
	}
}
