package de.gwdg.metadataqa.marc.definition.tags70x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.TaxonomicClassificationSourceCodes;

/**
 * Added Entry - Taxonomic Identification
 * http://www.loc.gov/marc/bibliographic/bd754.html
 */
public class Tag754 extends DataFieldDefinition {

	private static Tag754 uniqueInstance;

	private Tag754() {
		initialize();
	}

	public static Tag754 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag754();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "754";
		label = "Added Entry - Taxonomic Identification";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator();
		ind2 = new Indicator();
		setSubfieldsWithCardinality(
			"a", "Taxonomic name", "R",
			"c", "Taxonomic category", "R",
			"d", "Common or alternative name", "R",
			"x", "Non-public note", "R",
			"z", "Public note", "R",
			"0", "Authority record control number", "R",
			"2", "Source of taxonomic identification", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(TaxonomicClassificationSourceCodes.getInstance());
	}
}
