package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

public class Tag026 extends DataFieldDefinition {

	private static Tag026 uniqueInstance;

	private Tag026() {
		initialize();
	}

	public static Tag026 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag026();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "026";
		label = "Fingerprint Identifier";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("").setCodes(" ", "Undefined");
		// TODO: set label
		ind2 = new Indicator("").setCodes(" ", "Undefined");
		setSubfieldsWithCardinality(
			"a", "First and second groups of characters", "NR",
			"b", "Third and fourth groups of characters", "NR",
			"c", "Date", "NR",
			"d", "Number of volume or part", "R",
			"e", "Unparsed fingerprint", "NR",
			"2", "Source", "NR",
			"5", "Institution to which field applies", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		// TODO: 2 - code from http://www.loc.gov/standards/sourcelist/fingerprint.html
		getSubfield("5").setCodeList(OrganizationCodes.getInstance());
	}
}
