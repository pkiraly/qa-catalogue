package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Other Distinguishing Characteristics of Work or Expression
 * http://www.loc.gov/marc/bibliographic/bd381.html
 */
public class Tag381 extends DataFieldDefinition {
	private static Tag381 uniqueInstance;

	private Tag381() {
		initialize();
		postCreation();
	}

	public static Tag381 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag381();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "381";
		label = "Other Distinguishing Characteristics of Work or Expression";
		mqTag = "OtherDistinguishingCharacteristics";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd381.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Other distinguishing characteristic", "R",
			"u", "Uniform Resource Identifier", "R",
			"v", "Source of information", "R",
			"0", "Record control number", "R",
			"2", "Source of term", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());

		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("u").setMqTag("uri");
		getSubfield("v").setMqTag("sourceOfInformation");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("2").setMqTag("source");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
