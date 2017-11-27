package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Additional Dewey Decimal Classification Number
 * http://www.loc.gov/marc/bibliographic/bd083.html
 */
public class Tag083 extends DataFieldDefinition {

	private static Tag083 uniqueInstance;

	private Tag083() {
		initialize();
		postCreation();
	}

	public static Tag083 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag083();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "083";
		label = "Additional Dewey Decimal Classification Number";
		mqTag = "ClassificationAdditionalDdc";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd083.html";

		ind1 = new Indicator("Type of edition")
			.setCodes(
				"0", "Full edition",
				"1", "Abridged edition",
				"7", "Other edition specified in subfield $2"
			)
			.setMqTag("editionType");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Classification number", "R",
			"c", "Classification number--Ending number of span", "R",
			"m", "Standard or optional designation", "NR",
			"q", "Assigning agency", "NR",
			"y", "Table sequence number for internal subarrangement or add table", "R",
			"z", "Table identification", "R",
			"2", "Edition number", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("q").setCodeList(OrganizationCodes.getInstance());

		getSubfield("6").setContentParser(LinkageParser.getInstance());

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("c").setMqTag("endingNumber");
		getSubfield("m").setMqTag("standard");
		getSubfield("q").setMqTag("source");
		getSubfield("y").setMqTag("tableSequenceNumber");
		getSubfield("z").setMqTag("tableId");
		getSubfield("2").setMqTag("edition");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
