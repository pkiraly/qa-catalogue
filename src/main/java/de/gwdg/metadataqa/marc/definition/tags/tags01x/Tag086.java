package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.ClassificationSchemeSourceCodes;

/**
 * Government Document Classification Number
 * http://www.loc.gov/marc/bibliographic/bd086.html
 */
public class Tag086 extends DataFieldDefinition {

	private static Tag086 uniqueInstance;

	private Tag086() {
		initialize();
		postCreation();
	}

	public static Tag086 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag086();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "086";
		label = "Government Document Classification Number";
		bibframeTag = "Classification";
		mqTag = "GovernmentDocumentClassification";
		cardinality = Cardinality.Repeatable;

		ind1 = new Indicator("Number source").setCodes(
			" ", "Source specified in subfield $2",
			"0", "Superintendent of Documents Classification System",
			"1", "Government of Canada Publications: Outline of Classification"
		).setMqTag("numberSource");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Classification number", "NR",
			"z", "Canceled/invalid classification number", "R",
			"2", "Number source", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("2").setCodeList(ClassificationSchemeSourceCodes.getInstance());

		getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value");
		getSubfield("z").setMqTag("canceled");
		getSubfield("2").setMqTag("source");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
