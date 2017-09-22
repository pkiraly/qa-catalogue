package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectCategoryCodeSourceCodes;

/**
 * Subject Category Code
 * http://www.loc.gov/marc/bibliographic/bd072.html
 */
public class Tag072 extends DataFieldDefinition {

	private static Tag072 uniqueInstance;

	private Tag072() {
		initialize();
	}

	public static Tag072 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag072();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "072";
		label = "Subject Category Code";
		bibframeTag = "Subject";
		mqTag = "SubjectCategoryCode";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator();
		ind2 = new Indicator("Code source").setCodes(
			"0", "NAL subject category code list",
			"7", "Source specified in subfield $2"
		).setMqTag("codeSource");

		setSubfieldsWithCardinality(
			"a", "Subject category code", "NR",
			"x", "Subject category code subdivision", "R",
			"2", "Source", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("2").setCodeList(SubjectCategoryCodeSourceCodes.getInstance());

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("x").setMqTag("subdivision");
		getSubfield("2").setMqTag("source");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
