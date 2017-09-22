package de.gwdg.metadataqa.marc.definition.tags70x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.NameAndTitleAuthoritySourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;

/**
 * Added Entry - Hierarchical Place Name
 * http://www.loc.gov/marc/bibliographic/bd752.html
 */
public class Tag752 extends DataFieldDefinition {

	private static Tag752 uniqueInstance;

	private Tag752() {
		initialize();
	}

	public static Tag752 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag752();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "752";
		label = "Added Entry - Hierarchical Place Name";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator();
		ind2 = new Indicator();
		setSubfieldsWithCardinality(
			"a", "Country or larger entity", "R",
			"b", "First-order political jurisdiction", "NR",
			"c", "Intermediate political jurisdiction", "R",
			"d", "City", "NR",
			"e", "Relator term", "R",
			"f", "City subsection", "R",
			"g", "Other nonjurisdictional geographic region and feature", "R",
			"h", "Extraterrestrial area", "R",
			"0", "Authority record control number or standard number", "R",
			"2", "Source of heading or term", "NR",
			"4", "Relationship", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(NameAndTitleAuthoritySourceCodes.getInstance());
		getSubfield("4").setCodeList(RelatorCodes.getInstance());
	}
}
