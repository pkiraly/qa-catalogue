package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.DescriptionConventionSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

/**
 * Cataloging Source
 * http://www.loc.gov/marc/bibliographic/bd040.html
 */
public class Tag040 extends DataFieldDefinition {

	private static Tag040 uniqueInstance;

	private Tag040() {
		initialize();
	}

	public static Tag040 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag040();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "040";
		label = "Cataloging Source";
		cardinality = Cardinality.Nonrepeatable;
		ind1 = new Indicator("").setCodes(" ", "Undefined");
		ind2 = new Indicator("").setCodes(" ", "Undefined");
		setSubfieldsWithCardinality(
				"a", "Original cataloging agency", "NR",
				"b", "Language of cataloging", "NR",
				"c", "Transcribing agency", "NR",
				"d", "Modifying agency", "R",
				"e", "Description conventions", "R",
				"6", "Linkage", "NR",
				"8", "Field link and sequence number", "R"
		);
		getSubfield("b").setCodeList(LanguageCodes.getInstance());
		getSubfield("e").setCodeList(DescriptionConventionSourceCodes.getInstance());
		OrganizationCodes orgCodes = OrganizationCodes.getInstance();
		getSubfield("a").setCodeList(orgCodes);
		getSubfield("c").setCodeList(orgCodes);
		getSubfield("d").setCodeList(orgCodes);
	}
}
