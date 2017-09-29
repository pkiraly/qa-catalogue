package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.ThematicIndexCodeSourceCodes;

/**
 * Audience Characteristics
 * http://www.loc.gov/marc/bibliographic/bd385.html
 */
public class Tag385 extends DataFieldDefinition {
	private static Tag385 uniqueInstance;

	private Tag385() {
		initialize();
	}

	public static Tag385 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag385();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "385";
		label = "Audience Characteristics";
		bibframeTag = "IntendedAudience";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Audience term", "R",
			"b", "Audience code", "R",
			"m", "Demographic group term", "NR",
			"n", "Demographic group code", "NR",
			"0", "Authority record control number or standard number", "R",
			"2", "Source", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("2").setCodeList(ThematicIndexCodeSourceCodes.getInstance());

		getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value");
		getSubfield("b").setMqTag("audienceCode");
		getSubfield("m").setMqTag("demographicGroupTerm");
		getSubfield("n").setMqTag("demographicGroupCode");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("2").setMqTag("source");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
