package de.gwdg.metadataqa.marc.definition.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.ClassificationSchemeSourceCodes;

/**
 * Classification Numbers Assigned in Canada
 * http://www.loc.gov/marc/bibliographic/bd055.html
 */
public class Tag055 extends DataFieldDefinition {

	private static Tag055 uniqueInstance;

	private Tag055() {
		initialize();
	}

	public static Tag055 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag055();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "055";
		label = "Classification Numbers Assigned in Canada";
		bibframeTag = "ClassificationLcc";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator("Existence in LAC collection").setCodes(
			" ", "Information not provided",
			"0", "Work held by LAC",
			"1", "Work not held by LAC"
		).setMqTag("heldByLAC");
		ind2 = new Indicator("Type, completeness, source of class/call number").setCodes(
			"0", "LC-based call number assigned by LAC",
			"1", "Complete LC class number assigned by LAC",
			"2", "Incomplete LC class number assigned by LAC",
			"3", "LC-based call number assigned by the contributing library",
			"4", "Complete LC class number assigned by the contributing library",
			"5", "Incomplete LC class number assigned by the contributing library",
			"6", "Other call number assigned by LAC",
			"7", "Other class number assigned by LAC",
			"8", "Other call number assigned by the contributing library",
			"9", "Other class number assigned by the contributing library"
		).setMqTag("type");

		setSubfieldsWithCardinality(
			"a", "Classification number", "NR",
			"b", "Item number", "NR",
			"0", "Authority record control number or standard number", "R",
			"2", "Source of call/class number", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("2").setCodeList(ClassificationSchemeSourceCodes.getInstance());

		getSubfield("a").setBibframeTag("classificationPortion").setMqTag("rdf:value");
		getSubfield("b").setBibframeTag("itemPortion");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("2").setMqTag("source");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
