package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.GenreFormCodeAndTermSourceCodes;

/**
 * Content Type
 * http://www.loc.gov/marc/bibliographic/bd336.html
 */
public class Tag336 extends DataFieldDefinition {
	private static Tag336 uniqueInstance;

	private Tag336() {
		initialize();
	}

	public static Tag336 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag336();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "336";
		label = "Content Type";
		bibframeTag = "Content";
		mqTag = "ContentType";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Content type term", "R",
			"b", "Content type code", "R",
			"0", "Authority record control number or standard number", "R",
			"2", "Source", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("2").setCodeList(GenreFormCodeAndTermSourceCodes.getInstance());

		getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value");
		getSubfield("b").setMqTag("contentTypeCode");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("2").setBibframeTag("source");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
