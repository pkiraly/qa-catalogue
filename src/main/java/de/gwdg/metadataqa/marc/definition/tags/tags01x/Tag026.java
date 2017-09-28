package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.FingerprintSchemeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

/**
 * Fingerprint Identifier
 * http://www.loc.gov/marc/bibliographic/bd026.html
 */
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
		bibframeTag = "Fingerprint";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

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
		getSubfield("2").setCodeList(FingerprintSchemeSourceCodes.getInstance());
		getSubfield("5").setCodeList(OrganizationCodes.getInstance());

		getSubfield("a").setBibframeTag("rdf:value").setMqTag("firstAndSecondGroups");
		getSubfield("b").setBibframeTag("rdf:value").setMqTag("thirdAndFourthGroups");
		getSubfield("c").setBibframeTag("rdf:value").setMqTag("date");
		getSubfield("d").setBibframeTag("rdf:value").setMqTag("volume");
		getSubfield("e").setBibframeTag("rdf:value").setMqTag("unparsed");
		getSubfield("2").setBibframeTag("source");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
