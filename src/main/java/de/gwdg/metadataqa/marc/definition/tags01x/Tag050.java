package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Library of Congress Call Number
 * http://www.loc.gov/marc/bibliographic/bd050.html
 */
public class Tag050 extends DataFieldDefinition {

	private static Tag050 uniqueInstance;

	private Tag050() {
		initialize();
	}

	public static Tag050 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag050();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "050";
		label = "Library of Congress Call Number";
		bibframeTag = "ClassificationLcc";
		cardinality = Cardinality.Repeatable;
		ind1 = new Indicator("Existence in LC collection").setCodes(
			" ", "No information provided",
			"0", "Item is in LC",
			"1", "Item is not in LC"
		);
		ind2 = new Indicator("Source of call number").setCodes(
			"0", "Assigned by LC",
			"4", "Assigned by agency other than LC"
		);
		setSubfieldsWithCardinality(
			"a", "Classification number", "R",
			"b", "Item number", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("a").setBibframeTag("rdfs:label");
		getSubfield("b").setBibframeTag("itemPortion");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
