package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

/**
 * Dewey Decimal Classification Number
 * http://www.loc.gov/marc/bibliographic/bd082.html
 */
public class Tag082 extends DataFieldDefinition {

	private static Tag082 uniqueInstance;

	private Tag082() {
		initialize();
	}

	public static Tag082 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag082();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "082";
		label = "Dewey Decimal Classification Number";
		bibframeTag = "ClassificationDdc";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Type of edition").setCodes(
			"0", "Full edition",
			"1", "Abridged edition",
			"7", "Other edition specified in subfield $2"
		);
		ind2 = new Indicator("Source of classification number").setCodes(
			" ", "No information provided",
			"0", "Assigned by LC",
			"4", "Assigned by agency other than LC"
		);
		setSubfieldsWithCardinality(
			"a", "Classification number", "R",
			"b", "Item number", "NR",
			"m", "Standard or optional designation", "NR",
			"q", "Assigning agency", "NR",
			"2", "Edition number", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("q").setCodeList(OrganizationCodes.getInstance());
		getSubfield("a").setBibframeTag("classificationPortion").setMqTag("rdf:value");
		getSubfield("b").setBibframeTag("itemPortion");
		getSubfield("m").setMqTag("standard");
		getSubfield("q").setBibframeTag("source");
		getSubfield("2").setBibframeTag("edition");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
